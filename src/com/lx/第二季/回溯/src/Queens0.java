package com.lx.第二季.回溯.src;

public class Queens0 {
    /**
     * n 皇后问题(摆放在 n*n 的棋盘):每一行摆放一个皇后,皇后所在行列、正负对角线 不能有其他皇后
     * 计算 n 皇后在棋盘上共有多少种摆法
     *
     * @param n n个皇后
     */
    public static void placeQueens(int n) {
        if (n < 1) return;

        // 标记所有皇后摆放的位置
        // 数组索引表示皇后所在行,数组值表示皇后所在列
        // cols[0] = 0, 表示第一行第一列摆放了一个皇后
        cols = new int[n];

        place(0);
        System.out.println("共有 " + counts + " 种摆法~");
    }

    static int counts = 0; // 记录所有皇后有多少种摆法
    static int[] cols; // 记录所有皇后摆放的位置

    /**
     * 在当前行摆放一个皇后
     *
     * @param currentRow 当前行的行数
     */
    private static void place(int currentRow) {
        if (currentRow >= cols.length) { // 进入这里相当于成功得到一种摆法(因为第 n (索引为 n-1)行已摆完了)
            counts++;
            show();
            return;
        }

        // 一一试探当前行的每个合法列,试图摆放皇后
        for (int col = 0; col < cols.length; col++) {
            if (isValid(currentRow, col)) { // 当前列是否可摆放皇后
                cols[currentRow] = col; // 记录摆放位置(每次进行回溯后进行的新操作都会重新覆盖掉这里的旧操作,因此不需要还原现场)

                // 继续摆放下一行的皇后
                place(currentRow + 1); // 该函数调用完成相当于进行了回溯,继续试探当前行的下一个合法列
            }
        }
    }

    // 判断在 currentRow 行 col 列摆放皇后是否合法
    private static boolean isValid(int currentRow, int col) { // O(n)
        // 遍历 currentRow 之前行已经摆好的皇后所在位置
        for (int i = 0; i < currentRow; i++) {
            // 检测是否存在于所有摆放好的皇后的那一列
            if (cols[i] == col)
                return false;

            // 检测是否存在于所有摆放好的皇后的正负对角线:
            // 说明当前所选位置 (currentRow, col) 与已摆放的皇后位置 (i, cols[i]) 这两点所在直线的斜率为 1 或 -1
            // 注意这里整型除法的结果仍是整型,因此需要转为浮点型除法: 否则可能出现 3/2 == 1 为 true 的情况
            if (Math.abs((col - cols[i]) / (currentRow - i + 0.0)) == 1.0) {
                return false;
            }
        }

        return true;
    }

    // 打印摆放信息
    private static void show() {
        for (int row = 0; row < cols.length; row++) {
            for (int col = 0; col < cols.length; col++) {
                if (col == cols[row])
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
