package com.example.deptienda.utils

import java.text.NumberFormat
import java.util.Locale


fun Double.toChileanPesos(): String {
    val format = NumberFormat.getNumberInstance(Locale("es", "CL"))
    // Truncamos los decimales ya que en CLP no se suelen usar para precios generales.
    return "$ ${format.format(this.toInt())}"
}