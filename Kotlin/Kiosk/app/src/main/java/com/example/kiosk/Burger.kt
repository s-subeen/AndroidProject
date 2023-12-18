package com.example.kiosk

class Burger(id: Int, category: String, name: String, price: Int, explain: String) :
    AbstractMenu(id, category, name, price, explain) {
    override fun displayInfo(): String {
        val price = decimalFormat(price)
        return "%-10s | ₩ %s | %s".format(name, price, explain)
    }
}