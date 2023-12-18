package com.example.kiosk

class ProductManage {
    private val products: ArrayList<AbstractMenu> = initProduct()

    private fun initProduct(): ArrayList<AbstractMenu> {
        val products = ArrayList<AbstractMenu>()

        val burger = arrayListOf<AbstractMenu>(
            Burger(0, "burger", "Big Mac", 5500, "100% 순 쇠고기 패티 두 장에 치즈, 양상추, 피클"),
            Burger(1, "burger", "McCrispy Deluxe Burger", 6800, "100% 통다리살 케이준 치킨 패티, 포테이포 브리오쉬 번"),
            Burger(2, "burger", "McSpicy Shanghai Burger", 5500, "100% 닭가슴살 통살 위에 양상추, 토마토, 치킨 패티")
        )

        val beverage = arrayListOf<AbstractMenu>(
            Beverage(
                0,
                "beverage",
                "Americano",
                1000,
                "바로 내린 100% 친환경 커피로 더 신선하게! 더 풍부하게!",
                "Small"
            ),
            Beverage(
                1,
                "beverage",
                "Americano",
                1500,
                "바로 내린 100% 친환경 커피로 더 신선하게! 더 풍부하게!",
                "Medium"
            ),
            Beverage(
                2,
                "beverage",
                "Café Latte",
                1500,
                "바로 내린 100% 친환경 커피가 신선한 우유를 만나 더 신선하고 부드럽게!",
                "Small"
            ),
            Beverage(
                3,
                "beverage",
                "Café Latte",
                2000,
                "바로 내린 100% 친환경 커피가 신선한 우유를 만나 더 신선하고 부드럽게!",
                "Medium"
            ),
            Beverage(4, "beverage", "Coca-Cola", 1700, "갈증해소 뿐만이 아니라 기분까지 상쾌하게! 코카-콜라", "Small"),
            Beverage(5, "beverage", "Coca-Cola", 2200, "갈증해소 뿐만이 아니라 기분까지 상쾌하게! 코카-콜라", "Medium")
        )

        val fried = arrayListOf<AbstractMenu>(
            Fried(0, "fried", "Cheese Sticks", 2000, "자연 모짜렐라 치즈로 빈틈 없이 고소한 치즈스틱", "2조각"),
            Fried(1, "fried", "Cheese Sticks", 4200, "자연 모짜렐라 치즈로 빈틈 없이 고소한 치즈스틱", "4조각"),
            Fried(2, "fried", "McNuggets", 2600, "바삭하고 촉촉한 치킨이 한 입에 쏙", "4조각"),
            Fried(3, "fried", "McNuggets", 3800, "바삭하고 촉촉한 치킨이 한 입에 쏙", "6조각"),
        )

        products.addAll(burger)
        products.addAll(beverage)
        products.addAll(fried)

        return products
    }

    fun displayMenu(menu: String) {
        products.filter { it.category == menu }
            .forEach { println("${it.id + 1}. ${it.displayInfo()}") }
        println("%d. %-10s | %s".format(0, "뒤로가기", "뒤로가기"))
    }

    fun getSelectedItem(menu: String, id: Int): AbstractMenu? {
        return products.filter { it.category == menu }.find { it.id == id - 1 }
    }

    fun getMenuCount(menu: String): Int {
        return products.count { it.category == menu }
    }
}