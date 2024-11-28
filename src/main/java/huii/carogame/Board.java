/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package huii.carogame;

/**
 *
 * @author Huii
 */
public class Board {
    
    private int SIZE = 15;
    private String[][] board = new String[SIZE][SIZE];

    public Board()
    {
        board = new String[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                board[i][j] = "";
            }
        }
    }

    public boolean check(int x, int y, String str)
    {
        if (board[x][y].equals(""))
        {
            board[x][y] = str;
            return true;
        }
        return false;
        
    }

    public boolean checkWin(int x, int y, String str)
    {
        return checkDirection(x, y, str, 1, 0) ||
               checkDirection(x, y, str, 0, 1) ||
               checkDirection(x, y, str, 1, 1) ||
               checkDirection(x, y, str, 1, -1);
    }

    private boolean checkDirection(int x, int y, String str, int dx, int dy)
    {
        int count = 1;
        for (int i = 1; i < 5; i++)
        {
            int nx = x + i * dx, ny = y + i * dy;
            if (nx >= 0 && nx < SIZE && ny >= 0 && ny < SIZE && board[nx][ny].equals(str))
            {
                count++;
            }
            else
            {
                break;
            }
        }

        for (int i = 1; i < 5; i++)
        {
            int nx = x - i * dx, ny = y - i * dy;
            if (nx >= 0 && nx < SIZE && ny >= 0 && ny < SIZE && board[nx][ny].equals(str))
            {
                count++;
            }
            else
            {
                break;
            }
        }

        return count >= 5;
    }
}

