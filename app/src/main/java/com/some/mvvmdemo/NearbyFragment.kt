package com.some.mvvmdemo

import android.app.Activity
import android.app.Person
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.some.mvvmdemo.base.BaseFragment
import com.some.mvvmdemo.databinding.NearbyFragmentBinding
import com.some.mvvmdemo.entity.Account
import com.some.mvvmdemo.observer.AnimalBean
import com.some.mvvmdemo.observer.ConcreteObservable
import com.some.mvvmdemo.observer.PersonBean
import com.some.mvvmdemo.swipe.ItemDragListener
import com.some.mvvmdemo.swipe.ItemMoveListener
import com.some.mvvmdemo.swipe.MyItemTouchHelperCallback

import java.util.Collections

class NearbyFragment : BaseFragment(), View.OnClickListener, ItemDragListener, ItemMoveListener {
    lateinit var binding: NearbyFragmentBinding
    lateinit var nearbyVM: NearbyVM
    internal var adapter: NearbyAdapter? = null

    private var itemTouchHelper: ItemTouchHelper? = null
    private var callback: MyItemTouchHelperCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate ")
        nearbyVM = ViewModelProviders.of(this).get(NearbyVM::class.java)
        nearbyVM.initData()


        callback = MyItemTouchHelperCallback(this)
        itemTouchHelper = ItemTouchHelper(callback!!)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Log.d(TAG, "onCreateView ")
        binding = DataBindingUtil.inflate(inflater, R.layout.nearby_fragment, container,
                false)

        binding.click = this

        val linearLayoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL, false)
        binding.recyclerview.layoutManager = linearLayoutManager

        //添加自定义分割线
        val divider = DividerItemDecoration(mActivity!!,
                DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(mActivity!!, R.drawable.shape_divider_line)!!)
        binding.recyclerview.addItemDecoration(divider)

        itemTouchHelper!!.attachToRecyclerView(binding.recyclerview)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated ")
        nearbyVM.liveData.observe(this, Observer {
            if (adapter == null) {
                adapter = NearbyAdapter(nearbyVM.liveData.value,
                        this@NearbyFragment)
                binding.recyclerview.adapter = adapter
            } else {
                adapter!!.notifyDataSetChanged()
            }
        })

        val observerPerson = object : PersonBean<String>() {
            override fun dealWithEvent(s: String) {
                Log.d(TAG, "PersonBean dealWithEvent s=$s")
            }
        }

        val observerAnimal = object : AnimalBean<String>() {
            override fun dealWithEvent(s: String) {
                Log.d(TAG, "AnimalBean dealWithEvent s=$s")
            }
        }

        val observable = ConcreteObservable<String>()
        observable.registerObserver(observerPerson)
        observable.registerObserver(observerAnimal)

        observable.publishData("今天天气好")
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_remove -> Toast.makeText(activity, "点击remove", Toast.LENGTH_SHORT).show()
            R.id.btn_add -> nearbyVM.add()
        }
    }

    inner class EventListener {

        fun onClick(view: View) {

            Toast.makeText(activity, "点击", Toast.LENGTH_SHORT).show()

            when (view.id) {
                R.id.btn_add -> nearbyVM.add()
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated ")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach ")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d(TAG, "onHiddenChanged hidden = $hidden")
    }


    override fun onStartDrags(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper!!.startDrag(viewHolder)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(nearbyVM.liveData.value!!, fromPosition, toPosition)
        adapter!!.notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemRemoved(position: Int): Boolean {

        nearbyVM.liveData.value?.toMutableList()?.removeAt(position)
        adapter!!.notifyItemRemoved(position)
        return false
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.d(TAG, "setUserVisibleHint isVisibleToUser = $isVisibleToUser")
    }

    companion object {

        private val TAG = "xingtest-NearbyFragment"
    }


}
