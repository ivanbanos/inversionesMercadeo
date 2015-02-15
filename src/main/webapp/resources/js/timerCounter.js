/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var minutos = 16;
var segundos = 0;
function timer()
{
    if (segundos !== 0 || minutos !== 0) {
        segundos = segundos - 1;
        if (segundos <= -1)
        {
            segundos = 59;
            
            minutos = minutos - 1;
            if (minutos <= 0) {
                segundos = 0;
                miutos = 0;
            }
        }

    }

    if (segundos < 10) {
        document.getElementById("timer").innerHTML = minutos + ":0" + segundos;
    } else {
        document.getElementById("timer").innerHTML = minutos + ":" + segundos;
    }
}


