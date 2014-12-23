/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



function changeHasta(index) {
    var cantidad = $('#cantidad' + index + "");
    var hasta = $($('#hasta' + index).children()[0]);
    var desde = $('#desde' + index);
    var cantspan = $(cantidad.children()[0]);
    var cantinput = $(cantspan.children()[0]);

    var cantNumber = cantinput.val();
    var cantdesde = $(desde.children()[0]).text();

    if (cantNumber === '') {
        numbertoSum = 0;
    } else {
        var numbertoSum = parseInt(cantNumber);
    }

    var numberfrom = parseInt(cantdesde.substring(0, 4));
    var total = numberfrom + numbertoSum;
    var letra = cantdesde.substring(5, cantdesde.length).trim();
    
    letra = sumarLetra(letra, total);
    total = total%10000;
    if (total <10) {
        hasta.text('000' + total+"-"+letra);
    } else
    if (total < 100) {
        hasta.text('00' + total+"-"+letra);
    } else if (total < 1000) {
        hasta.text('0' + total+"-"+letra);
    } else {
        hasta.text(total+"-"+letra);
    }
}

function sumarLetra(letra, total) {
    var factor = 0;
    var cantidad = 0;
    var i = letra.length-1;
    var nuevaletra = "";
    for(;i>=0;i--){
        cantidad += mapLetrasValores[letra.substring(i,i+1)]*(Math.pow(26, factor));
        factor += 1;
        
    }
    cantidad = parseInt(cantidad + (total/10000));
    var letraactual;
    while(cantidad!==0){
        nuevaletra = mapValoresLetras[cantidad%26] + nuevaletra;
        letraactual = mapValoresLetras[cantidad%26];
        cantidad = parseInt(cantidad/26);
        if(letraactual === "Z" && cantidad === 1){
            break;
            
        }
    }
    return nuevaletra;
}


var mapLetrasValores = {"A": 1, "B": 2, "C": 3, "D": 4, "E": 5, "F": 6, 
               "G": 7, "H": 8, "I": 9, "J": 10, "K": 11, "L": 12, 
               "M": 13, "N": 14, "O": 15, "P": 16, "Q": 17, "R": 18, 
               "S": 19, "T": 20, "U": 21, "V": 22, "W": 23, "X": 24, 
               "Y": 25, "Z": 26};
var mapValoresLetras = {1: "A", 2: "B", 3: "C", 4: "D", 5: "E", 6: "F", 
               7: "G", 8: "H", 9: "I", 10: "J", 11: "K", 12: "L", 
               13: "M", 14: "N", 15: "O", 16: "P", 17: "Q", 18: "R", 
               19: "S", 20: "T", 21: "U", 22: "V", 23: "W", 24: "X", 
               25: "Y", 0: "Z"};