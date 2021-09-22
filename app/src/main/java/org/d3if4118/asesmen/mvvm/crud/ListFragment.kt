package org.d3if4118.asesmen.mvvm.crud

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.d3if3118.asesmen.R
import org.d3if4118.asesmen.core.BaseFragment
import com.d3if3118.asesmen.databinding.FragmentListBinding
import org.d3if4118.asesmen.model.Note


class ListFragment : BaseFragment<FragmentListBinding>() {

    private lateinit var mNoteViewModel: NoteViewModel
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup
    ): FragmentListBinding {
        return FragmentListBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
        // UserViewModel
        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java).apply {
            readAllData.observe(viewLifecycleOwner, {
                setupRV(it)
            })
        }

    }

    override fun setupUI(savedInstanceState: Bundle?) {

        (activity as AppCompatActivity).supportActionBar?.apply {
            title = "List"
            setDisplayShowCustomEnabled(true)
        }

        binding?.fabAddItem?.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment2)
        }

        setHasOptionsMenu(true)

    }

    private fun setupRV(user: List<Note>) {
        // Recyclerview
        val adapter = ListNoteAdapter()
        adapter.setData(user)
        binding?.apply {
            rvNote.adapter = adapter
            gridLayoutManager =
                GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            rvNote.layoutManager = gridLayoutManager
            rvNote.setHasFixedSize(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete -> {
                deleteAll()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mNoteViewModel.deleteAllNote()
            Toast.makeText(requireContext(), "Success removed everything", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure want to delete everything?")
        builder.create().show()
    }

}