//用いる色
const HIGHLIGHT_LC_COLOR = 'rgb(230, 230, 230)';
const HIGHLIGHT_CELL_COLOR = 'rgb(180, 180, 180)';
const CLICKED_CELL_COLOR = 'rgb(170, 230, 250)';

//クリックされたセルの座標
let clickedCellCol = null;
let clickedCellRow = null;


//カーソルをセルに置いたときに行列をハイライトする
function highlightLowColHandler() {
    const table = this.table;
    const i = this.i;
    const j = this.j;
    
    //行
    for(let k = 0; k < table.rows[i].cells.length; k++){
        const newColor = (i == clickedCellCol && k == clickedCellRow) ? CLICKED_CELL_COLOR : HIGHLIGHT_LC_COLOR;
        table.rows[i].cells[k].style.backgroundColor = newColor;
    }
    //列
    for(let k = 0; k < table.rows.length; k++){
        const newColor = (k == clickedCellCol && j == clickedCellRow) ? CLICKED_CELL_COLOR : HIGHLIGHT_LC_COLOR;
        table.rows[k].cells[j].style.backgroundColor = newColor; 
    }

    //ホバーしているセル
    const newColor = (i == clickedCellCol && j == clickedCellRow) ? CLICKED_CELL_COLOR : HIGHLIGHT_CELL_COLOR;
    table.rows[i].cells[j].style.backgroundColor = newColor;
}

//カーソルがセルから離れたときにハイライトを戻す。
function cancelHighlightHandler(){
    const table = this.table;
    const i = this.i;
    const j = this.j;

    //クリックされてた場合は、その色に戻す
    //行
    for(let k = 0; k < table.rows[i].cells.length; k++){
        const newColor = (i == clickedCellCol && k == clickedCellRow) ? CLICKED_CELL_COLOR : '';
        table.rows[i].cells[k].style.backgroundColor = newColor;
    }
    //列
    for(let k = 0; k < table.rows.length; k++){
        const newColor = (k == clickedCellCol && j == clickedCellRow) ? CLICKED_CELL_COLOR : '';
        table.rows[k].cells[j].style.backgroundColor = newColor; 
    }

    //ホバーしているセル
    const newColor = (i == clickedCellCol && j == clickedCellRow) ? CLICKED_CELL_COLOR : '';
    table.rows[i].cells[j].style.backgroundColor = newColor;

}
//セルをクリックしたときに色を変え、座標を記録
function clickCellHandler(){
    const table = this.table;
    const i = this.i;
    const j = this.j;
    
    //すでにクリックされていた場合
    if (i == clickedCellCol && j == clickedCellRow){
        clickedCellCol = null;
        clickedCellRow = null;
        table.rows[i].cells[j].style.backgroundColor = '';
    }
    else {
        if(clickedCellCol != null && clickedCellRow != null){
            table.rows[clickedCellCol].cells[clickedCellRow].style.backgroundColor = "";  
        }
        clickedCellCol = i;
        clickedCellRow = j;
        table.rows[i].cells[j].style.backgroundColor = CLICKED_CELL_COLOR;  
    }
}

//セルの表示を数字とメモで切り替える
//参考: https://qiita.com/Gakusyu/items/0aa06abb12d3fb3d53a6

function changeToNum(table, i, j) {
    let numdiv = table.rows[i].cells[j].children[0];
    let memodiv = table.rows[i].cells[j].children[1]; 

    numdiv.classList.add('active'); 
    memodiv.classList.remove('active'); 
}

function changeToMemo(table, i, j) {
    let numdiv = table.rows[i].cells[j].children[0];
    let memodiv = table.rows[i].cells[j].children[1]; 

    numdiv.classList.remove('active'); 
    memodiv.classList.add('active'); 
}


//数字ボタンが押された時の処理
function numButtonHandler(){
    const table = this.table;
    const num = this.num;
    if(clickedCellCol != null && clickedCellRow != null){
        let numcell = table.rows[clickedCellCol].cells[clickedCellRow].children[0].children[0];
        numcell.rows[0].cells[0].innerText = num;
        changeToNum(table, clickedCellCol, clickedCellRow);
    }
}

//DELETEボタンが押された時の処理
function delButtonHandler(){
    const table = this.table;
    if(clickedCellCol != null && clickedCellRow != null){
        let numcell = table.rows[clickedCellCol].cells[clickedCellRow].children[0].children[0];
        numcell.rows[0].cells[0].innerText = '';
        changeToMemo(table, clickedCellCol, clickedCellRow);
    }
}

//MEMOボタンが押された時の処理
//未完成!!
function memoButtonHandler(){
    const table = this.table;
    if(clickedCellCol != null && clickedCellRow != null){
        memotable = table.rows[clickedCellCol].cells[clickedCellRow].children[1].children[0];

        for(let i = 0; i < 3; i++){
            for(let j = 0; j < 3; j++){
                memotable.rows[i].cells[j].innerText = i * 3 + j + 1;
            }
        }
        changeToMemo(table, clickedCellCol, clickedCellRow);
    }
}


//フィールドの各要素にイベントハンドラを設定
//TODO: memo実装に伴い、fieldの構造が変化。それに対応
const table = document.getElementsByClassName('field').item(0);
for(let i = 0; i < table.rows.length; i++){
    let cells = table.rows[i].cells;
    for(let j = 0; j < cells.length; j++){
        let cell = cells[j];

        //addEventlistenerのハンドラに引数を渡す
        cell.addEventListener('click', {table: table, i: i, j: j, handleEvent: clickCellHandler});
        cell.addEventListener('mouseleave', {table: table, i: i, j: j, handleEvent: cancelHighlightHandler});
        cell.addEventListener('mouseover', {table: table, i: i, j: j, handleEvent: highlightLowColHandler});
    }
}

//各操作用ボタンにイベントハンドラを設定
const numbuttons = document.getElementsByClassName('numbutton');
for(let i = 0; i < 9; i++){
    numbuttons.item(i).addEventListener('click', {table: table, num: i+1, handleEvent: numButtonHandler});
}

const optionbuttons = document.getElementsByClassName('optionbutton');
//MEMOボタン

optionbuttons.item(0).addEventListener('click', {table: table, handleEvent: memoButtonHandler});
//DELETEボタン
optionbuttons.item(1).addEventListener('click', {table: table, handleEvent: delButtonHandler});