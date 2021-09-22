package org.d3if4118.asesmen.mvvm.crud

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.d3if3118.asesmen.R
import com.d3if3118.asesmen.databinding.FragmentImportantDialogBinding
import com.d3if3118.asesmen.databinding.FragmentUpdateBinding
import org.d3if4118.asesmen.core.BaseFragment
import org.d3if4118.asesmen.model.Note
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class UpdateFragment : BaseFragment<FragmentUpdateBinding>() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mNoteViewModel: NoteViewModel


    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup
    ): FragmentUpdateBinding {
        return FragmentUpdateBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = "Update"
            setDisplayShowCustomEnabled(true)
        }
        binding?.apply {
            etTitleEdit.setText(args.currentNote.title)
            etNoteEdit.setText(args.currentNote.note)
            etImportantEdit.setText(args.currentNote.status)
            etImportantEdit.setOnClickListener {
                showDialog()
            }
        }
        setHasOptionsMenu(true)
    }

    private fun showDialog() {
        //Inflate the dialog with custom view
        val mDialogView = FragmentImportantDialogBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(mDialogView.root)
        //show dialog
        val mAlertDialog = mBuilder.show()
        //login button click of custom layout
        mDialogView.btnYes.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
            //get text from EditTexts of custom layout
            val yesString = resources.getString(R.string.yes)
            //set the input text in TextView
            binding?.etImportantEdit?.setText(yesString.toString())

        }
        //cancel button click of custom layout
        mDialogView.btnNo.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
            //get text from EditTexts of custom layout
            val noString = resources.getString(R.string.no)
            //set the input text in TextView
            binding?.etImportantEdit?.setText(noString.toString())
        }
        mDialogView.icClosePicture.setOnClickListener {
            mAlertDialog.dismiss()
        }

    }


    @SuppressLint("NewApi")
    private fun updateItem() {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val date = current.format(formatter)
        val titleNoteEdit = binding?.etTitleEdit?.text.toString()
        val valueNoteEdit = binding?.etNoteEdit?.text.toString()
        val important = binding?.etImportantEdit?.text.toString()

        if (inputCheck(titleNoteEdit)) {
            val noteUpdate = Note(args.currentNote.id, titleNoteEdit, valueNoteEdit, date, important)
            mNoteViewModel.updateNote(noteUpdate)
            Toast.makeText(requireContext(), "Update is successfully", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } else {
            Toast.makeText(requireContext(), "Field cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(titleNoteEdit: String): Boolean {
        return !(TextUtils.isEmpty(titleNoteEdit))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete -> {
                deleteNote()
                true
            }
            R.id.menu_add -> {
                updateItem()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mNoteViewModel.deleteNote(args.currentNote)
            Toast.makeText(
                requireContext(),
                "Success removed: ${args.currentNote.title}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().popBackStack().not()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentNote.title}")
        builder.setMessage("Are you sure want to delete ${args.currentNote.title}?")
        builder.create().show()
    }

}