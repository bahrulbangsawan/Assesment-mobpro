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
import com.d3if3118.asesmen.R
import org.d3if4118.asesmen.core.BaseFragment
import com.d3if3118.asesmen.databinding.FragmentAddBinding
import com.d3if3118.asesmen.databinding.FragmentImportantDialogBinding
import org.d3if4118.asesmen.model.Note
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class AddFragment : BaseFragment<FragmentAddBinding>() {

    private lateinit var mNoteViewModel: NoteViewModel


    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup
    ): FragmentAddBinding {
        return FragmentAddBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = "Create"
            setDisplayShowCustomEnabled(true)
        }

        binding?.etImportant?.setOnClickListener{
            showDialog()
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
        val  mAlertDialog = mBuilder.show()
        //login button click of custom layout
        mDialogView.btnYes.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
            //get text from EditTexts of custom layout
            val yesString = resources.getString(R.string.yes)
            //set the input text in TextView
            binding?.etImportant?.setText(yesString)

        }
        //cancel button click of custom layout
        mDialogView.btnNo.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
            //get text from EditTexts of custom layout
            val noString = resources.getString(R.string.no)
            //set the input text in TextView
            binding?.etImportant?.setText(noString)
        }
        mDialogView.icClosePicture.setOnClickListener{
            mAlertDialog.dismiss()
        }

    }

    @SuppressLint("NewApi")
    private fun insertData() {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = current.format(formatter)

        val status = binding?.etImportant?.text.toString()
        val titleNote = binding?.etTitle?.text.toString()
        val valueNote = binding?.etNote?.text.toString()

        if (checkInput(titleNote)) {
            val note = Note(
                0,
                titleNote,
                valueNote,
                formatted,
                status
            )

            mNoteViewModel.addNote(note)
            Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } else {
            Toast.makeText(requireContext(), "Field cannot by empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkInput(titleNote: String): Boolean {
        return !(TextUtils.isEmpty(titleNote))
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                insertData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}