package com.example.kiosk

class MenuManager(private val abstractMenu: AbstractMenu) {
    fun displayInfo(): String {
        return abstractMenu.displayInfo()
    }
}