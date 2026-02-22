package com.darusc.mousedroid.fragments

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.darusc.mousedroid.mkinput.GestureHandler
import com.darusc.mousedroid.R
import com.darusc.mousedroid.databinding.FragmentTouchpadBinding
import androidx.core.view.isGone
import androidx.core.view.setPadding
import androidx.fragment.app.activityViewModels
import androidx.transition.TransitionManager
import com.darusc.mousedroid.viewmodels.TouchpadViewModel

class Touchpad : Fragment() {

    private val TAG = "Mousedroid"
    private lateinit var binding: FragmentTouchpadBinding

    private val viewModel: TouchpadViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_touchpad, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup touchpad gesture listener and button listeners
        binding.touchpadSensor.setOnTouchListener(viewModel.createGestureHandler(requireContext()))

        // Multimedia dropdown
        binding.btnToggleMedia.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.root as ViewGroup)

            val isHidden = binding.mediaControls.isGone
            binding.mediaControls.visibility = if (isHidden) View.VISIBLE else View.GONE
            binding.btnToggleMedia.setIconResource(
                if (isHidden) R.drawable.ic_arrow_drop_up else R.drawable.ic_arrow_drop_down
            )
        }

        // Touchpad fullscreen toggle
        binding.btnFullscreen.setOnClickListener {
            val currentOrientation = requireActivity().requestedOrientation

            if(currentOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                updateLayoutForOrientation(false)
            } else {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                updateLayoutForOrientation(true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Reset the orientation when leaving the Touchpad
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    private fun updateLayoutForOrientation(landscape: Boolean) {
        val rootLayout = binding.root as ConstraintLayout
        val container = binding.touchpadContainer
        val params = container.layoutParams as ConstraintLayout.LayoutParams

        val marginTopPx = (24 * resources.displayMetrics.density).toInt()
        val marginBottomPx = (16 * resources.displayMetrics.density).toInt()
        val rootPaddingPx = (16 * resources.displayMetrics.density).toInt()

        if (landscape) {
            rootLayout.setPadding(0, 0, 0, 0)

            // Expand to fill the screen
            params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            params.setMargins(0, 0, 0, 0)

            // Remove all other buttons
            binding.title.visibility = View.GONE
            binding.mouseButtonsRow.visibility = View.GONE
            binding.btnToggleMedia.visibility = View.GONE
            binding.mediaControls.visibility = View.GONE
            binding.btnFullscreen.setIconResource(R.drawable.ic_fullscreen_exit)
        } else {
            rootLayout.setPadding(rootPaddingPx, rootPaddingPx, rootPaddingPx, rootPaddingPx)

            // Set the layout params for portrait mode
            params.topToBottom = R.id.title
            params.bottomToTop = R.id.mouseButtonsRow
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            params.topMargin = marginTopPx
            params.bottomMargin = marginBottomPx

            // Make everything else visible again
            binding.title.visibility = View.VISIBLE
            binding.mouseButtonsRow.visibility = View.VISIBLE
            binding.btnToggleMedia.visibility = View.VISIBLE
            binding.mediaControls.visibility = View.VISIBLE
            binding.btnFullscreen.setIconResource(R.drawable.ic_fullscreen)
        }

        container.layoutParams = params
    }
}
