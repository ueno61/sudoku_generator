//用いる色
const HIGHLIGHT_LC_COLOR = 'rgb(230, 230, 230)';
const HIGHLIGHT_CELL_COLOR = 'rgb(180, 180, 180)';
const CLICKED_CELL_COLOR = 'rgb(170, 230, 250)';
const MEMO_MODE_BG_COLOR = 'rgb(60 60 60)';
const MEMO_MODE_CHAR_COLOR = 'rgb(230, 230, 230)';

//フィールドとボタンのエレメント
const table = document.getElementsByClassName('field').item(0);
const numbuttons = document.getElementsByClassName('numbutton');
const optionbuttons = document.getElementsByClassName('optionbutton');

//クリックされたセルの座標
let clickedCellCol = null;
let clickedCellRow = null;

//メモモードか否か
let memoMode = false;


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

//memoする数字からmemotableの入力位置を返す
// [1-9] --> [[0-2], [0-2]]
function getPlaceInMemoTable(num){
    return [Math.floor((num - 1) / 3), (num - 1) % 3];
}

//memotableにnumを記述
//numがすでに書かれている場合は消す。
function writeToMemoTable(cell, num){
    const place = getPlaceInMemoTable(num);
    memocell = cell.children[1].children[0].rows[place[0]].cells[place[1]];
    if(memocell.innerText == ''){
        memocell.innerText = num;
    }
    else {
        memocell.innerText = '';
    }
}

//memotableを全消し
function clearMemoTable(cell){
    memotable = cell.children[1].children[0];
    for(let i = 0; i < 3; i++){
        for(let j = 0; j < 3; j++){
            memotable.rows[i].cells[j].innerText = '';
        }
    }
}

//----↓ イベントハンドラ ↓----

//カーソルをセルに置いたときに行列をハイライトする
function highlightLowColHandler() {
    const table = this.table;
    const i = this.i;
    const j = this.j;
    
    //TODO: newcolor はラムダ式で定義

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


//数字ボタンが押された時の処理
function numButtonHandler(){
    const table = this.table;
    const num = this.num;

    //クリックされてない場合何もしない
    if(clickedCellCol == null || clickedCellRow == null) return;

    const cell = table.rows[clickedCellCol].cells[clickedCellRow];

    if(memoMode){
        //メモモード
        //普通の数字が書かれていた場合は消す仕様
        let numcell = cell.children[0].children[0];
        numcell.rows[0].cells[0].innerText = ''; 

        writeToMemoTable(cell, num);
        changeToMemo(table, clickedCellCol, clickedCellRow);
    }
    else {
        //通常モード
        const numcell = cell.children[0].children[0];
        numcell.rows[0].cells[0].innerText = num;
        changeToNum(table, clickedCellCol, clickedCellRow);   
    }

}

//DELETEボタンが押された時の処理
function delButtonHandler(){
    const table = this.table;

    //クリックされてない場合何もしない
    if(clickedCellCol == null || clickedCellRow == null) return;

    const cell = table.rows[clickedCellCol].cells[clickedCellRow];

    if(memoMode){
        //メモモード
        clearMemoTable(cell);
    }
    else{
        //通常モード
        let numcell = cell.children[0].children[0];
        numcell.rows[0].cells[0].innerText = '';
        changeToMemo(table, clickedCellCol, clickedCellRow);

    }
}

//MEMOボタンが押された時、MEMOモードと通常モードを切り替える
//未完成!!
function memoButtonHandler(){
    const memobutton = optionbuttons[0];
    newBgColor = (memoMode) ? '' : MEMO_MODE_BG_COLOR;
    newFontColor = (memoMode) ? '' : MEMO_MODE_CHAR_COLOR; 
    memobutton.style.backgroundColor = newBgColor;
    memobutton.style.color = newFontColor;
    memoMode = !memoMode;
}


//フィールドの各要素にイベントハンドラを設定
//TODO: memo実装に伴い、fieldの構造が変化。それに対応

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
for(let i = 0; i < 9; i++){
    numbuttons.item(i).addEventListener('click', {table: table, num: i+1, handleEvent: numButtonHandler});
}

//MEMOボタン
optionbuttons.item(0).addEventListener('click', {handleEvent: memoButtonHandler});
//DELETEボタン
optionbuttons.item(1).addEventListener('click', {table: table, handleEvent: delButtonHandler});