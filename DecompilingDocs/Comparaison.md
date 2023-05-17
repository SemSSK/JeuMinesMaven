# Comparaison

cette section portera sur la comparaison entre le code ecrit manuellement et le code generer par la décompilation des fichier .class. 5 decompilateurs seront utiliser qui sont : CFR, Fernflower, Jadx, JDCore, Procyon ,afin mettre en lumiére les différence entre chaqu'un d'entre eux.

## CFR

<table>
<tr>
<td>Code Source</td>
<td>CFR</td>
<td>Commentaire</td>
</tr>
<tr>
<td>
<code lang="language-java">
private static final long serialVersionUID = 6195235521361212179L;
private final int NUM_IMAGES = 13;
private final int CELL_SIZE = 15;
private final int COVER_FOR_CELL = 10;
private final int MARK_FOR_CELL = 10;
private final int EMPTY_CELL = 0;
private final int MINE_CELL = 9;
private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

private final int DRAW_MINE = 9;
private final int DRAW_COVER = 10;
private final int DRAW_MARK = 11;
private final int DRAW_WRONG_MARK = 12;

private int[] field;
private boolean inGame;
private int mines_left;
private Image[] img;
private int mines = 40;
private int rows = 16;
private int cols = 16;
private int all_cells;
private JLabel statusbar;
</code>
</td>
</tr>
</table>

