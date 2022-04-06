package com.lx.第二季.回溯.src;

public class Queens2 {
    static int counts = 0; // 记录所有皇后有多少种摆法

    static byte cols; // 每一位记录每一列是否摆放了皇后
    static short mainDiagonal; // 每一位记录每一主对角线是否摆放了皇后
    static short subDiagonal; // 每一位记录每一次对角线是否摆放了皇后

    static int[] queens; // 记录所有皇后的摆放位置(主要为了打印)

    /**
     * 8 皇后问题:进一步压缩空间复杂度
     * 仅适用于 8 皇后问题, n 皇后问题需要修改下代码
     */
    public static void placeQueens() {
        queens = new int[8];

        place(0);
        System.out.println("共有 " + counts + " 种摆法~");
    }

    /**
     * 在当前行摆放一个皇后
     *
     * @param currentRow 当前行的行数
     */
    private static void place(int currentRow) {
        if (currentRow >= 8) {
            counts++;
            show();
            return;
        }

        // 一一试探当前行的每个合法列,试图摆放皇后
        for (int col = 0; col < 8; col++) {
            /* 剪枝判断: O(1) */

            byte colIndex = (byte) (1 << col);
            // 判断 col 列是否已摆放皇后(判断 cols 的第 col 位是否为 1)
            if ((cols & colIndex) == colIndex) continue;

            // 计算出 (currentRow, col) 位置的主对角线索引
            short mainIndex = (short) (1 << (currentRow + col));
            // 判断主对角线是否已摆放皇后(判断 mainDiagonal 的第 mainIndex 位是否为1)
            if ((mainDiagonal & mainIndex) == mainIndex) continue;

            // 计算出 (currentRow, col) 位置的次对角线索引
            short subIndex = (short) (1 << (currentRow + 7 - col));
            // 判断次对角线是否已摆放皇后(判断 subDiagonal 的第 subIndex 位是否为1)
            if ((subDiagonal & subIndex) == subIndex) continue;

            // 摆放皇后(将对应位都设为1)
            cols |= colIndex;
            mainDiagonal |= mainIndex;
            subDiagonal |= subIndex;

            queens[col] = col;

            // 继续摆放下一行的皇后
            place(currentRow + 1); // 继续摆放下一轮

            // 回溯之后的还原现场(将对应位都设为0)
            cols &= ~colIndex;
            mainDiagonal &= ~mainIndex;
            subDiagonal &= ~subIndex;
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
        placeQueens();
    }
}
