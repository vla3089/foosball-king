package com.vladds.foosballking.presentation

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import com.vladds.foosballking.R

class SaveItemMenuProvider(private val onSaveClicked: () -> Unit) : MenuProvider {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.save_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.save) {
            onSaveClicked()
        }
        return true
    }
}
