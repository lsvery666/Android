package com.bytedance.viewpagertest03

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class MyFragment(val idx: Int) : Fragment() {

    private val TAG = "MyFragment$idx"
    lateinit var title: TextView
    lateinit var editText: EditText
    lateinit var button: Button
    lateinit var testView: EditText

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById<TextView>(R.id.title)
        title.text = idx.toString()
        Log.d(TAG, "onViewCreated")

        editText = view.findViewById(R.id.edit_text)

        editText.setOnFocusChangeListener { v, hasFocus ->
            Log.d(TAG, "editText focus: $hasFocus")
        }

//        val container = Utils.makeTextView(requireContext())
//        testView = container.findViewById(R.id.testText)
//        (view as ViewGroup).addView(container)

//        button = view.findViewById<Button>(R.id.button)
//
//        button.setOnClickListener {
//            title.text = "changed"
//            testView.text = "changed"
//        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }

}