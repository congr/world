import java.util.List;

class nqueen {
    static public List<List<String>> solveNQueens(int n) {
        char[][] board = new char[n][n];
        count = 0;
        dfs(board, 0);
        System.out.println(count);
        return null;
    }

    static int count;

    static void dfs(char[][] board, int row) {
        if (row >= board.length) {
            count++;
            return;
        }

        for (int col = 0; col < board.length; col++) {
            if (validate(board, row, col)) {
                board[row][col] = 'Q';
                dfs(board, row + 1);
                board[row][col] = '.';
            }
        }
    }

    static boolean validate(char[][] board, int row, int col) {
        for(int i=0; i<row; i++)
            for(int j=0; j<board.length; j++){
                if(board[i][j]=='Q' && (Math.abs(row-i) == Math.abs(col-j) || col == j))
                    return false;
            }
        return true;

    }

    public static void main(String[] args) {
        solveNQueens(5);
    }
}