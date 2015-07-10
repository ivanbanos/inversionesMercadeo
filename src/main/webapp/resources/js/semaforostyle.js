/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function setStyle(id, cant, min) {
    if (cant <= min) {
        $("#" + id).css('color', 'red');
    } else {
        $("#" + id).css('color', 'green');
    }
}
