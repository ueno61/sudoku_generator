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

//セルがクリックされているかを判定
function isCellClicked(){
    return (clickedCellCol != null && clickedCellRow != null);
}

//memoする数字からmemotableの入力位置を返す
// [1-9] --> [[0-2], [0-2]]
function getPlaceInMemoTable(num){
    return [Math.floor((num - 1) / 3), (num - 1) % 3];
}

//通常モード時セルにnumを記述。numに''を指定することでclear機能も兼任
function writeToNumCell(cell, num){
    numcell = cell.children[0].children[0].rows[0].cells[0];
    numcell.innerText = num;
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

//セルの背景を変更
function setCellBgColor(cell, color){
    cell.style.backgroundColor = color;
}

//----↓ イベントハンドラ ↓----

//カーソルをセルに置いたときに行列をハイライトする
function highlightLowColHandler() {
    const table = this.table;
    const i = this.i;
    const j = this.j;
    
    const newColor = (i, j, center) => {
        return (i == clickedCellCol && j == clickedCellRow) ? CLICKED_CELL_COLOR : 
               (center) ? HIGHLIGHT_CELL_COLOR : HIGHLIGHT_LC_COLOR; 
    }

    //行
    for(let k = 0; k < table.rows[i].cells.length; k++){
        const cell = table.rows[i].cells[k];
        setCellBgColor(cell, newColor(i, k, false));
    }
    //列
    for(let k = 0; k < table.rows.length; k++){
        const cell = table.rows[k].cells[j];
        setCellBgColor(cell, newColor(k, j, false)); 
    }

    //ホバーしているセル
    const cell = table.rows[i].cells[j];
    setCellBgColor(cell, newColor(i, j, true));
}

//カーソルがセルから離れたときにハイライトを戻す。
function cancelHighlightHandler(){
    const table = this.table;
    const i = this.i;
    const j = this.j;

    const newColor = (i, j) => {
        return (i == clickedCellCol && j == clickedCellRow) ? CLICKED_CELL_COLOR : '';
    }

    //クリックされてた場合は、その色に戻す
    //行
    for(let k = 0; k < table.rows[i].cells.length; k++){
        const cell = table.rows[i].cells[k] 
        setCellBgColor(cell, newColor(i, k));
    }
    //列
    for(let k = 0; k < table.rows.length; k++){
        const cell = table.rows[k].cells[j];
        setCellBgColor(cell, newColor(k, j)); 
    }

    //ホバーしているセル
    const cell = table.rows[i].cells[j] 
    setCellBgColor(cell, newColor(i, j));

}
//セルをクリックしたときに色を変え、座標を記録
function clickCellHandler(){
    const table = this.table;
    const oldclickedCellCol = clickedCellCol;
    const oldclickedCellRow = clickedCellRow;
    clickedCellCol  = this.i;
    clickedCellRow = this.j;
    const clickedcell = table.rows[clickedCellCol].cells[clickedCellRow];
    
    //すでにそのマスがクリックされていた場合
    if (oldclickedCellCol == clickedCellCol && oldclickedCellRow == clickedCellRow){
        clickedCellCol = null;
        clickedCellRow = null;
        setCellBgColor(clickedcell, '');
        return;
    }

    //前にクリックされていたセルの色を戻す
    if(oldclickedCellCol != null && oldclickedCellRow != null){
        const oldclickedcell = table.rows[oldclickedCellCol].cells[oldclickedCellRow];
        setCellBgColor(oldclickedcell, '');  
    }

    //色を変える
    setCellBgColor(clickedcell, CLICKED_CELL_COLOR);  
}


//数字ボタンが押された時の処理
function numButtonHandler(){
    const table = this.table;
    const num = this.num;

    //クリックされてない場合何もしない
    if(!isCellClicked()) return;

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
        writeToNumCell(cell,num);
        changeToNum(table, clickedCellCol, clickedCellRow);   
    }

}

//DELETEボタンが押された時の処理
function delButtonHandler(){
    const table = this.table;

    //クリックされてない場合何もしない
    if(!isCellClicked) return;

    const cell = table.rows[clickedCellCol].cells[clickedCellRow];

    if(memoMode){
        //メモモード
        clearMemoTable(cell);
        changeToNum(table, clickedCellCol, clickedCellRow);
    }
    else{
        //通常モード
        writeToNumCell(cell, '');
        changeToMemo(table, clickedCellCol, clickedCellRow);

    }
}

//MEMOボタンが押された時、MEMOモードと通常モードを切り替える
function memoButtonHandler(){
    //ボタンの色の変更処理
    const memobutton = optionbuttons[0];
    newBgColor = (memoMode) ? '' : MEMO_MODE_BG_COLOR;
    newFontColor = (memoMode) ? '' : MEMO_MODE_CHAR_COLOR; 
    memobutton.style.backgroundColor = newBgColor;
    memobutton.style.color = newFontColor;

    memoMode = !memoMode;
}


//フィールドの各要素にイベントハンドラを設定

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