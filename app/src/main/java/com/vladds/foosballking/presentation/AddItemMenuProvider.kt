package com.vladds.foosballking.presentation

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import com.vladds.foosballking.R

class AddItemMenuProvider(
    private val onAddClicked: () -> Unit
) : MenuProvider {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.add_item_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.add) {
            onAddClicked()
        }
        return true
    }
}
