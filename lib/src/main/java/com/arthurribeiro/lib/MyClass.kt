package com.arthurribeiro.lib

fun main(){
    var readLine = ""
    var number1 = 0
    var number2 = 0

    println("Digite o primeiro número: ")
    readLine = readLine().toString()
    if (readLine != ""){
        number1 = readLine.toInt()
    }

    println("Digite o segundo número: ")
    readLine = readLine().toString()
    if (readLine != ""){
        number2 = readLine.toInt()
    }

    square(number1, number2)

}

fun square(a: Int, b: Int){
     if (a == b && a != 0 && b != 0){
        println("Figura é um triângulo")
    } else {
        println("Figura não é um triângulo")
    }
}