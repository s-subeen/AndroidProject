package com.example.kiosk

class Fried(id: Int, category: String, name: String, price: Int, description: String, private val size: String) :
    AbstractMenu(id, category, name, price, description) {
    override fun displayInfo(): String {
        val price = decimalFormat(price)
        return if (size.isEmpty()) {
            "%-10s | ₩ %s | %s".format(name, price, explain)
        } else {
            "%s - %-5s | ₩ %s | %s".format(name, size, price, explain)
        }
    }
}