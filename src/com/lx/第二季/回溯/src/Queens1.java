package com.lx.第二季.回溯.src;

public class Queens1 {
    static int counts = 0; // 记录所有皇后有多少种摆法

    static boolean[] cols; // 记录每一列是否摆放了皇后
    static boolean[] mainDiagonal; // 记录每一主对角线是否摆放了皇后
    static boolean[] subDiagonal; // 记录每一次对角线是否摆放了皇后

    static int[] queens; // 记录所有皇后的摆放位置(主要为了打印)

    /**
     * n 皇后问题:优化剪枝判断
     *
     * @param n n个皇后
     */
    public static void placeQueens(int n) {
        if (n < 1) return;

        cols = new boolean[n];
        mainDiagonal = new boolean[(n << 1) - 1]; // 共有 2n - 1 条主对角线
        subDiagonal = new boolean[mainDiagonal.length];

        queens = new int[n];

        place(0);
        System.out.println("共有 " + counts + " 种摆法~");
    }

    /**
     * 在当前行摆放一个皇后
     *
     * @param currentRow 当前行的行数
     */
    private static void place(int currentRow) {
        if (currentRow >= cols.length) {
            counts++;
            show();
            return;
        }

        // 一一试探当前行的每个合法列,试图摆放皇后
        for (int col = 0; col < cols.length; col++) {
            // 剪枝判断: O(1)
            if (cols[col]) continue; // 判断 col 列是否已摆放皇后

            int mainIndex = currentRow + col; // 计算出所选择位置的主对角线索引
            if (mainDiagonal[mainIndex]) continue; // 判断主对角线是否已摆放皇后

            int subIndex = currentRow + cols.length - 1 - col; // 计算出 (currentRow, col) 位置的次对角线索引
            if (subDiagonal[subIndex]) continue; // 判断次对角线是否已摆放皇后

            // 摆放皇后
            cols[col] = true;
            mainDiagonal[mainIndex] = true;
            subDiagonal[subIndex] = true;

            queens[col] = col;

            // 继续摆放下一行的皇后
            place(currentRow + 1); // 继续摆放下一轮

            // 回溯之后的还原现场:
            // 为什么这里就需要还原现场,因为之后再次选择的位置不会索引到上述三个数组上次所选择的同一位置
            // (新数据不会覆盖掉旧数据,因此手动需要清除旧数据)
            cols[col] = false;
            mainDiagonal[mainIndex] = false;
            subDiagonal[subIndex] = false;
        }
    }

    // 打印摆放信息
    private static void show() {
        for (int row = 0; row < queens.length; row++) {
            for (int col = 0; col < queens.length; col++) {
                if (col == queens[row])
                    System.out.print("1 ");
                else
                    System.out.print("0 ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        placeQueens(8);
    }
}
