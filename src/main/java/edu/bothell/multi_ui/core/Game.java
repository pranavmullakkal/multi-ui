package edu.bothell.multi_ui.core;

import java.util.ArrayList;


public class Game {
    private final int                  MAX_PLAYERS = 3;
    private final ArrayList<Player>    p;
    private final State                s;
    private int                        turn;
    private Player                     active;

    public Game(Control c){
        this.turn = 0;
        this.s = new World();
        this.p = new ArrayList<>();
    }
    
    public Player addPlayer(Player p){
        this.p.add(p);
        if(this.active == null) active = p;

        return p;
    }

    public Player addPlayer(char c, String sId){
        Player p = new Player(c);
        p.setSId(sId);
        return addPlayer(p);
    }

    public char[] getPlayersChar(){
        char[] pcs = new char[p.size()];
        for(int i = 0; i < pcs.length; i++) 
            pcs[i] = p.get(i).getChar();
        
        return pcs;
    }
    
    public boolean isValid(int[] pos, String sId){
        System.out.println("isVAlid?"+s.getIt(pos)+"|" + sId+"|" + active.getSId()+"|");

        if(pos[0] > 3 && pos[1] > 3) System.out.println("HECK NO");;

        return s.isOpen(pos) && active.getSId().equals(sId);
    }
    
    public boolean checkWin(char player) {
        char[][] board = s.getIt();
        
        // Check rows
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == player && 
                board[row][1] == player && 
                board[row][2] == player) {
                return true;
            }
        }
        
        // Check columns
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == player && 
                board[1][col] == player && 
                board[2][col] == player) {
                return true;
            }
        }
        
        // Check diagonals
        if (board[0][0] == player && 
            board[1][1] == player && 
            board[2][2] == player) {
            return true;
        }
        
        if (board[0][2] == player && 
            board[1][1] == player && 
            board[2][0] == player) {
            return true;
        }
        
        return false;
    }

    private Player winner = null;

    public Player announceWinner(Player winningPlayer) {
        this.winner = winningPlayer;
        System.out.println("Player " + winningPlayer.getChar() + " has won the game!");
        return winner;
    }

    public Player getWinner() {
        return winner;
    }

    


    public char play(int[] pos, String sId) {
        if(!isValid(pos, sId)) return ' ';
        turn++;
        this.s.setIt(active.getChar(), pos[0], pos[1]);
        
        if (checkWin(active.getChar())) {
            announceWinner(active);
            return active.getChar();
        }
        
        this.active = p.get(turn % p.size());
        return active.getChar();
    }
    

    public Player getActive() {
        return this.active;
    }

    public State getState() {
        return this.s;
    }

    public Location getLocation(int x, int y) {
        return ((World)s).getLocation(x, y);
    }

    public int getMaxPlayers() {
        return MAX_PLAYERS;
    }

    public int getPlayerCount() {
        return p.size();
    }

    public ArrayList<Player> getPlayers(){
        return this.p;
    }

    public int getTurn(){
        return this.turn;
    }


}
