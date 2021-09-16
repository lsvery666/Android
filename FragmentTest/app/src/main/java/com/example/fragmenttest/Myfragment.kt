package com.example.fragmenttest

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


class Myfragment(var text:String, val visible: Boolean = false) : Fragment() {

    private var textView: TextView? = null
    private var fragment_button: Button? = null

    companion object{
        const val TAG = "MyFragment"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "$text: onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "$text: onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_myfragment, container, false)
        textView = view.findViewById(R.id.tv)
        fragment_button = view.findViewById(R.id.fragment_button)
        updateText(text)
        fragment_button?.visibility = if(visible){
            fragment_button?.setOnClickListener {
                if(activity != null){
                    val mainActivity = activity as MainActivity
                    with(mainActivity.supportFragmentManager){
                        val fragment1 = findFragmentByTag("Fragment1") as Myfragment?
                        val fragment2 = findFragmentByTag("Fragment2") as Myfragment?
                        val fragment3 = findFragmentByTag("Fragment3") as Myfragment?
                        fragment1?.updateText("hhhhhhh1")
                        fragment2?.updateText("hhhhhhh2")
                        fragment3?.updateText("hhhhhhh3")
                    }
                }
            }
            View.VISIBLE
        } else View.INVISIBLE
        Log.d(TAG, "$text: onCreateView")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "$text: onViewCreated")
    }

    // 过时
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "$text: onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "$text: onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "$text: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "$text: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "$text: onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "$text: onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "$text: onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "$text: onDetach")
    }

    fun updateText(str: String) {
        text = str
        textView!!.text = str
    }
}