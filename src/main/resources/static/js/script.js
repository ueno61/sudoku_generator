const HIGHLIGHT_LC_COLOR = 'rgb(230, 230, 230)';
const HIGHLIGHT_CELL_COLOR = 'rgb(180, 180, 180)';

//カーソルをセルに置いたときに行列をハイライトする
function highlightlowcol(e) {
    const table = this.table;
    const i = this.i;
    const j = this.j;
    
    //行
    for(let k = 0; k < table.rows[i].cells.length; k++){
            table.rows[i].cells[k].style.backgroundColor = HIGHLIGHT_LC_COLOR;
        }
    //列
    for(let k = 0; k < table.rows.length; k++){
            table.rows[k].cells[j].style.backgroundColor = HIGHLIGHT_LC_COLOR; 
        }

    //ホバーしているセル
    table.rows[i].cells[j].style.backgroundColor = HIGHLIGHT_CELL_COLOR;
}

//カーソルがセルから離れたときにハイライトを戻す。
function cancelhighlight(e){
    const table = this.table;
    const i = this.i;
    const j = this.j;

    //行
    for(let k = 0; k < table.rows[i].cells.length; k++){
        table.rows[i].cells[k].style.backgroundColor = "";
    }
    //列
    for(let k = 0; k < table.rows.length; k++){
        table.rows[k].cells[j].style.backgroundColor = ""; 
    }

    //ホバーしているセル
    table.rows[i].cells[j].style.backgroundColor = '';

}

// //ページのロードが終わってから実行
// window.onload = (() => {
    //テーブルの各要素にイベントハンドラを設定
    const table = document.getElementsByClassName('field').item(0);
        for(let i = 0; i < table.rows.length; i++){
            let cells = table.rows[i].cells;
            for(let j = 0; j < cells.length; j++){
                let cell = cells[j];
                //addEventlistenerのハンドラに引数を渡す
                cell.addEventListener('mouseleave', {table: table, i: i, j: j, handleEvent: cancelhighlight});
                cell.addEventListener('mouseover', {table: table, i: i, j: j, handleEvent: highlightlowcol});
            }
        }
// })