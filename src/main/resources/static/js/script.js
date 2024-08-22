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


//テーブルの各要素にイベントハンドラを設定
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