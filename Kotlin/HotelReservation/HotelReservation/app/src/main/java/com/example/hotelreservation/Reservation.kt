package com.example.hotelreservation

import java.time.DateTimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main() {
    val peoples = arrayListOf<People>()
    val bookingList = arrayListOf<BookingList>()
    while (true) {
        println(
            "호텔 예약 프로 그램 입니다.\n" +
                    "[메뉴]\n" +
                    "1. 방예약, 2. 예약 목록 출력, 3. 예약 목록 (정렬) 출력, 4. 시스템 종료, 5. 금액 입금-출금 내역 목록 출력 6. 예약 변경/취소"
        )

        when (readln().toInt()) {
            1 -> {
                var roomNum: Int
                var checkIn: LocalDate
                var checkOut: LocalDate

                println("예약자분의 성함을 입력해주세요.")
                val name: String = readln()

                while (true) {
                    println("예약할 방번호를 입력해주세요.")
                    val room = readln()
                    if (!room.isNumber()) {
                        println("방번호 입력은 숫자만 가능합니다.")
                    }

                    if (room.toInt() < 100 || room.toInt() > 999) {
                        println("올바르지 않은 방번호 입니다. 방 번호는 100~999 영역 이내 입니다.")
                        continue
                    } else {
                        roomNum = room.toInt()
                        break
                    }
                }

                while (true) {
                    println("체크인 날짜를 입력해주세요 표기형식. 20231130")
                    val date = readln()
                    if (date.dateValidation()) {
                        checkIn = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"))
                    } else {
                        continue
                    }

                    if (compareTo(checkIn, LocalDate.now()) < 0) {
                        println("체크인은 지난 날은 선택할 수 없습니다. $date")
                        continue
                    }
                    val list = bookingList.filter { it.room == roomNum }
                    if (list.isNotEmpty()) {
                        var used = true
                        for (element in list) {
                            if (element.checkIn <= checkIn && checkIn <= element.checkOut) {
                                used = false
                                println("해당 날짜에 이미 방을 사용 중 입니다. 다른 날짜를 입력해 주세요.")
                                continue
                            }
                        }
                        if (used) break
                    } else {
                        break
                    }
                }

                while (true) {
                    println("체크아웃 날짜를 입력해주세요 표기형식. 20231130")
                    val date = readln()
                    if (date.dateValidation()) {
                        checkOut =
                            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"))
                    } else {
                        continue
                    }

                    if (compareTo(checkOut, checkIn) <= 0) {
                        println("체크아웃 날짜는 체크인 날짜보다 이전이거나 같을 수는 없습니다.")
                        continue
                    } else {
                        val list = bookingList.filter { it.room == roomNum }
                        if (list.isNotEmpty()) {
                            var used = true
                            for (element in list) {
                                val reserved = dateList(element.checkIn, element.checkOut)
                                val current = dateList(checkIn, checkOut)
                                if (current.any { it in reserved }) {
                                    used = false
                                    println("해당 날짜에 이미 방을 사용 중 입니다. 다른 날짜를 입력해 주세요.")
                                    continue
                                }
                            }
                            if (used) break
                        } else {
                            break
                        }
                    }
                }

                var people = peoples.find { it.name == name }
                if (people == null) {
                    people = People(name = name)
                    peoples.add(people)
                }

                BookingList(
                    people,
                    roomNum,
                    checkIn,
                    checkOut
                ).run {
                    bookingList.add(this)
                }

                println("호텔 예약이 완료되었습니다.")
            }

            2 -> {
                if (bookingList.isEmpty()) {
                    println("예약 목록이 없습니다.")
                    break
                }
                println("호텔 예약자 목록입니다.")
                bookingList.forEachIndexed { i, item ->
                    println("${i + 1}. 사용자: ${item.people.name}, 방번호: ${item.room}, 체크인: ${item.checkIn}, 체크아웃: ${item.checkOut}")
                }
            }

            3 -> {
                if (bookingList.isEmpty()) {
                    println("예약 목록이 없습니다.")
                    break
                }
                println("호텔 예약자 목록입니다. (정렬완료)")
                val sortedList = bookingList.sortedBy { it.checkIn }
                sortedList.forEachIndexed { i, item ->
                    println("${i + 1}. 사용자: ${item.people.name}, 방번호: ${item.room}, 체크인: ${item.checkIn}, 체크아웃: ${item.checkOut}")
                }
            }

            4 -> {
                println("호텔 예약이 종료되었습니다.")
                break
            }

            5 -> {
                // TODO
                val name = readln()
                val people = peoples.find { it.name == name }
                if (people == null) {
                    println("예약된 사용자를 찾을 수 없습니다.")
                } else {

                }


            }

            6 -> {
                // TODO
            }
        }
    }
}

private fun String.isNumber(): Boolean {
    return try {
        this.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

private fun String.dateValidation(): Boolean {
    return try {
        LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyyMMdd"))
        true
    } catch (e: DateTimeException) {
        println("잘 못 된 날짜 형식 입니다.")
        false
    }
}

private fun compareTo(date1: LocalDate, date2: LocalDate): Int {
    return date1.compareTo(date2)
}

private fun dateList(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
    val mutableList = mutableListOf<LocalDate>()
    var date = startDate
    mutableList.add(date)
    while (compareTo(date, endDate) != 0) {
        date = date.plusDays(1)
        mutableList.add(date)
    }
    return mutableList
}







