
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;

class grid {

    public int[][] arr;

    public grid() {
        this.arr = new int[3][3];
    }

    public int full() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (arr[i][j] == 0) {
                    return 0;
                }
            }
        }
        return 1;
    }

    public boolean col(int player) {
        int flag;
        for (int i = 0; i < 3; i++) {
            flag = 0;
            for (int j = 0; j < 3; j++) {
                if (player == arr[j][i]) {
                    flag++;
                }
            }
            if (flag == 3) {
                return true;
            }
        }
        return false;

    }

    public boolean row(int player) {
        int flag;
        for (int i = 0; i < 3; i++) {
            flag = 0;
            for (int j = 0; j < 3; j++) {
                if (player == arr[i][j]) {
                    flag++;
                }
            }
            if (flag == 3) {
                return true;
            }

        }
        return false;
    }

    public boolean cross1(int player) {
        return player == arr[0][0] && player == arr[1][1] && player == arr[2][2];

    }

    public boolean cross2(int player) {
        return player == arr[2][0] && player == arr[1][1] && player == arr[0][2];
    }

    public int check(int player) {
        if (col(player) || row(player) || cross1(player) || cross2(player)) {
            return 1;
        }
        return 0;

    }

    public void win(int player) {
        if (player == -1) {
            JOptionPane.showMessageDialog(null, "player: O win!!!");
        } else {
            JOptionPane.showMessageDialog(null, "player: X win!!!");
        }
    }

}

class human {

    public int player_val;

    public human(int player) {
        this.player_val = player;
    }

    public void play(int[][] arr, int[] loc) {
        if (arr[loc[0]][loc[1]] == 0) {
            arr[loc[0]][loc[1]] = player_val;
        }
    }
}

class Robot {

    public int[] loc;
    public int me;
    public int[] last_move_loc;

    public Robot(int me) {
        loc = new int[2];
        this.me = me;
        last_move_loc = new int[2];
        last_move_loc[0] = -1;
        last_move_loc[1] = -1;
    }

    public void playbot(int[][] arr, int opponent, int flag) {
        if (winchance(arr, opponent) == 1) {
            return;
        }
        if (defence(arr, opponent) == 1) {
            return;
        }
        if (nextmove(arr, opponent, flag) == 1) {
            return;
        }
    }

    public boolean col(int[][] arr, int robot_me, int opponent) {
        int flag;
        for (int i = 0; i < 3; i++) {
            flag = 0;
            for (int j = 0; j < 3; j++) {
                if (opponent == arr[j][i]) {
                    flag++;
                } else if (robot_me == arr[j][i]) {
                    flag--;
                } else {
                    loc[0] = j;
                    loc[1] = i;
                }
            }
            if (flag == 2) {
                return true;
            }
        }
        return false;

    }

    public boolean row(int[][] arr, int robot_me, int opponent) {
        int flag;
        for (int i = 0; i < 3; i++) {
            flag = 0;
            for (int j = 0; j < 3; j++) {
                if (opponent == arr[i][j]) {
                    flag++;
                } else if (robot_me == arr[i][j]) {
                    flag--;
                } else {
                    loc[0] = i;
                    loc[1] = j;
                }
            }
            if (flag == 2) {
                return true;
            }

        }
        return false;
    }

    public boolean cross1(int[][] arr, int robot_me, int opponent) {
        int flag = 0;
        if ((robot_me == arr[0][0]) || (robot_me == arr[1][1]) || (robot_me == arr[2][2])) {
            return false;
        }
        if (opponent == arr[0][0]) {
            flag++;
        } else {
            loc[0] = 0;
            loc[1] = 0;
        }

        if (opponent == arr[1][1]) {
            flag++;
        } else {
            loc[0] = 1;
            loc[1] = 1;
        }

        if (opponent == arr[2][2]) {
            flag++;
        } else {
            loc[0] = 2;
            loc[1] = 2;
        }

        return flag == 2;
    }

    public boolean cross2(int[][] arr, int robot_me, int opponent) {
        int flag = 0;
        if ((robot_me == arr[2][0]) || (robot_me == arr[1][1]) || (robot_me == arr[0][2])) {
            return false;
        }
        if (opponent == arr[2][0]) {
            flag++;
        } else {
            loc[0] = 2;
            loc[1] = 0;
        }

        if (opponent == arr[1][1]) {
            flag++;
        } else {
            loc[0] = 1;
            loc[1] = 1;
        }

        if (opponent == arr[0][2]) {
            flag++;
        } else {
            loc[0] = 0;
            loc[1] = 2;
        }

        return flag == 2;
    }

    public int check(int[][] arr, int robot_me, int opponent) {
        if (col(arr, robot_me, opponent)) {
            return 1;
        }
        if (row(arr, robot_me, opponent)) {
            return 1;
        }
        if (cross1(arr, robot_me, opponent)) {
            return 1;
        }
        if (cross2(arr, robot_me, opponent)) {
            return 1;
        }

        return 0;

    }

    public int defence(int[][] arr, int opponent) //uses the check ,  row, column, cross1, cross2, all it is i.e
    {																//i.e robot_me ,  player
        if (check(arr, me, opponent) == 1) {
            arr[loc[0]][loc[1]] = me;
            last_move_loc[0] = loc[0];
            last_move_loc[1] = loc[1];
            return 1;
        }

        return 0;

    }

    public int winchance(int[][] arr, int opponent) {
        if (check(arr, opponent, me) == 1) {
            arr[loc[0]][loc[1]] = me;
            last_move_loc[0] = loc[0];
            last_move_loc[1] = loc[1];
            return 1;
        }

        return 0;

    }

    public boolean row_check(int[][] arr, int i, int opponent) {
        for (int j = 0; j < 3; j++) {
            if (arr[i][j] == opponent) {
                return false;
            } else if (arr[i][j] == me) {
                continue;
            } else {
                loc[0] = i;
                loc[1] = j;
            }

        }
        arr[loc[0]][loc[1]] = me;
        last_move_loc[0] = loc[0];
        last_move_loc[1] = loc[1];
        return true;
    }

    public boolean col_check(int[][] arr, int j, int opponent) {
        for (int i = 0; i < 3; i++) {
            if (arr[i][j] == opponent) {
                return false;
            } else if (arr[i][j] == me) {
                continue;
            } else {
                loc[0] = i;
                loc[1] = j;
            }

        }

        arr[loc[0]][loc[1]] = me;
        last_move_loc[0] = loc[0];
        last_move_loc[1] = loc[1];
        return true;

    }

    public boolean cross1_check(int[][] arr, int opponent) {
        for (int i = 0; i < 3; i++) {
            if (arr[i][i] == opponent) {
                return false;
            } else if (arr[i][i] == me) {
                continue;
            } else {
                loc[0] = i;
                loc[1] = i;

            }
        }
        last_move_loc[0] = loc[0];
        last_move_loc[1] = loc[1];
        arr[loc[0]][loc[1]] = me;
        return true;
    }

    public boolean cross2_check(int[][] arr, int opponent) {

        if ((arr[2][0] == opponent) || (arr[1][1] == opponent) || (arr[0][2] == opponent)) {
            return false;
        } else if (arr[2][0] == me) {
            loc[0] = 1;
            loc[1] = 1;
        } else if (arr[1][1] == me) {
            loc[0] = 0;
            loc[1] = 2;
        } else {
            loc[0] = 1;
            loc[1] = 1;
        }

        last_move_loc[0] = loc[0];
        last_move_loc[1] = loc[1];
        arr[loc[0]][loc[1]] = me;
        return true;
    }

    public int last_move_check(int[][] arr, int opponent) {
        if (last_move_loc[0] == -1) {
            return 0;
        } else {
            if (row_check(arr, last_move_loc[0], opponent) || col_check(arr, last_move_loc[1], opponent) || cross1_check(arr, opponent) || cross2_check(arr, opponent)) {
                return 1;
            }
            return 0;
        }
    }

    public int nextmove(int[][] arr, int opponent, int flag) {

        if ((arr[1][1] == 0) && (flag != 1)) {
            arr[1][1] = me;
            last_move_loc[0] = 1;
            last_move_loc[1] = 1;
            return 1;
        } else if (last_move_check(arr, opponent) == 1) {
            return 1;
        } else {
            Random rand = new Random();
            int reference;
            while (true) {
                reference = rand.nextInt(10);
                switch (reference) {
                    case 1:
                        if (arr[0][0] == 0) {
                            arr[0][0] = me;
                            last_move_loc[0] = 0;
                            last_move_loc[1] = 0;
                            return 1;
                        }
                    case 2:
                        if (arr[0][1] == 0) {
                            arr[0][1] = me;
                            last_move_loc[0] = 0;
                            last_move_loc[1] = 1;
                            return 1;
                        }
                    case 3:
                        if (arr[0][2] == 0) {
                            arr[0][2] = me;
                            last_move_loc[0] = 0;
                            last_move_loc[1] = 2;
                            return 1;
                        }
                    case 4:
                        if (arr[1][0] == 0) {
                            arr[1][0] = me;
                            last_move_loc[0] = 1;
                            last_move_loc[1] = 0;
                            return 1;
                        }
                    case 5:
                        if (arr[1][1] == 0) {
                            arr[1][1] = me;
                            last_move_loc[0] = 1;
                            last_move_loc[1] = 1;
                            return 1;
                        }
                    case 6:
                        if (arr[1][2] == 0) {
                            arr[1][2] = me;
                            last_move_loc[0] = 1;
                            last_move_loc[1] = 2;
                            return 1;
                        }
                    case 7:
                        if (arr[2][0] == 0) {
                            arr[2][0] = me;
                            last_move_loc[0] = 2;
                            last_move_loc[1] = 0;
                            return 1;
                        }
                    case 8:
                        if (arr[2][1] == 0) {
                            arr[2][1] = me;
                            last_move_loc[0] = 2;
                            last_move_loc[1] = 1;
                            return 1;
                        }
                    case 9:
                        if (arr[2][2] == 0) {
                            arr[2][2] = me;
                            last_move_loc[0] = 2;
                            last_move_loc[1] = 2;
                            return 1;
                        }
                    default:
                        continue;
                }
            }

        }

    }

    public int[] getlocation() {
        return last_move_loc;
    }

}

class Expert_bot extends Robot {

    private int roundPlayedByBot;

    public Expert_bot(int me) {
        super(me);
        roundPlayedByBot = 0;
    }

    public void playbot(int[][] arr, int opponent, int flag) {
        if (winchance(arr, opponent) == 1) {
            roundPlayedByBot++;
            return;
        }

        if (defence(arr, opponent) == 1) {
            roundPlayedByBot++;
            return;
        }

        if (nextmove1(arr, opponent) == 1) {
            roundPlayedByBot++;
            return;
        }

        if (nextmove(arr, opponent, flag) == 1) {
            roundPlayedByBot++;
            return;
        }
    }

    public int nextmove1(int[][] arr, int opponent) {

        if (playable() <= iscornerSqaureEmpty(arr, me)) {
            return playcorner(arr, me, opponent);
        }
        return -1;
    }

    public int playcorner(int[][] arr, int player, int opponent) {
        int refernece = 0;
        switch (roundPlayedByBot) {
            case 0:

                if (((ismiddleSquareUsed(arr, opponent) == true) || ((iscornerSqaureEmpty(arr, opponent)) == 2)) && (arr[1][1] == 0)) {

                    arr[1][1] = me;
                    last_move_loc[0] = 1;
                    last_move_loc[1] = 1;
                    return 1;
                }
                Random rand = new Random();
                while (true) {
                    refernece = rand.nextInt(10);

                    switch (refernece) {
                        case 1:
                            if ((arr[0][0] == 0)) {
                                arr[0][0] = player;
                                last_move_loc[0] = 0;
                                last_move_loc[1] = 0;
                                return 1;
                            }
                        case 3:
                            if ((arr[0][2] == 0)) {
                                arr[0][2] = player;
                                last_move_loc[0] = 0;
                                last_move_loc[1] = 2;
                                return 1;
                            }
                        case 7:
                            if ((arr[2][0] == 0)) {
                                arr[2][0] = player;
                                last_move_loc[0] = 2;
                                last_move_loc[1] = 0;
                                return 1;
                            }
                        case 9:
                            if ((arr[2][2] == 0)) {
                                arr[2][2] = player;
                                last_move_loc[0] = 2;
                                last_move_loc[1] = 2;
                                return 1;
                            }

                    }
                }
            case 1:

                if ((ismiddleSquareUsed(arr, opponent) == true) && (arr[1][1] == 0)) {

                    arr[1][1] = me;
                    last_move_loc[0] = 1;
                    last_move_loc[1] = 1;
                    return 1;
                } else if (addAtCorner(arr, opponent) == true) {
                    return 1;
                } else {
                    return 0;
                }
            case 2:

                if (addAtCorner(arr, opponent) == true) {
                    return 1;
                } else {
                    return 0;
                }
        }
        return 0;
    }

    public boolean addAtCorner(int arr[][], int opponent) {
        int flag = 0;
        int sum = last_move_loc[0] + last_move_loc[1];
        while (flag != 2) {
            switch (sum) {
                case 0:
                    if (arr[2][2] == 0) {
                        arr[2][2] = me;
                        last_move_loc[0] = 2;
                        last_move_loc[1] = 2;
                        return true;
                    }
                case 2:
                    if (last_move_loc[0] - last_move_loc[1] == 2) {
                        if (arr[0][2] == 0) {
                            arr[0][2] = me;
                            last_move_loc[0] = 0;
                            last_move_loc[1] = 2;
                            return true;
                        }
                    } else if (arr[2][0] == 0) {
                        arr[2][0] = me;
                        last_move_loc[0] = 2;
                        last_move_loc[1] = 0;
                        return true;
                    }
                case 4:
                    if (arr[0][0] == 0) {
                        arr[0][0] = me;
                        last_move_loc[0] = 0;
                        last_move_loc[1] = 0;
                        return true;
                    }
            }
            sum--;
            if (sum < 0) {
                sum = 4;
            }
        }
        return false;
    }

    public boolean ismiddleSquareUsed(int[][] arr, int opponent) {
        return (arr[1][0] == opponent) || (arr[0][1] == opponent) || (arr[2][1] == opponent) || (arr[1][2] == opponent);
    }

    public int iscornerSqaureEmpty(int[][] arr, int player) {
        int squares_left = 0;
        int squares_played = 0;
        for (int i = 0; i <= 2; i += 2) {
            for (int j = 0; j <= 2; j += 2) {
                if (arr[i][j] == 0) {
                    squares_left++;
                } else if (arr[i][j] == player) {
                    squares_played++;
                }
            }
        }
        return (squares_left + squares_played);
    }

    public int playable() {
        switch (roundPlayedByBot) {
            case 0:
                return 4;
            case 1:
                return 3;
            case 2:
                return 3;
        }
        return -1;
    }
}

class gui extends JFrame implements ActionListener {

    private static JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, bstart, brestart;
    private JCheckBox chuman1, chuman2, ccpu1, ccpu2, cexpert1, cexpert2;
    private JLabel label, label1, score;
    private ButtonGroup bg1, bg2;
    private int player, sum, scoreOfX, scoreOfO;
    private BlockingQueue<int[]> qu = new ArrayBlockingQueue<int[]>(9);
    private JMenuBar bar;
    private JMenu menu;
    private JMenuItem item;
    private volatile Boolean flag = false;

    public gui() {
        super("Tic Tac Toe");
        setVisible(true);
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        item = new JMenuItem("About");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "This is My First Game Project In Java using Swing \u2764\uFE0F");
            }
        });
        menu = new JMenu("About");
        bar = new JMenuBar();
        menu.add(item);
        bar.add(menu);
        rootPane.setJMenuBar(bar);
        player = 1;
        sum = 0;
        scoreOfO = 0;
        scoreOfX = 0;

        b1 = new JButton("");
        b2 = new JButton("");
        b3 = new JButton("");
        b4 = new JButton("");
        b5 = new JButton("");
        b6 = new JButton("");
        b7 = new JButton("");
        b8 = new JButton("");
        b9 = new JButton("");

        b1.setBounds(10, 120, 65, 65);
        b2.setBounds(80, 120, 65, 65);
        b3.setBounds(150, 120, 65, 65);
        b4.setBounds(10, 190, 65, 65);
        b5.setBounds(80, 190, 65, 65);
        b6.setBounds(150, 190, 65, 65);
        b7.setBounds(10, 260, 65, 65);
        b8.setBounds(80, 260, 65, 65);
        b9.setBounds(150, 260, 65, 65);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);

        bstart = new JButton("Start");
        brestart = new JButton("Restart");
        bstart.setBounds(10, 350, 100, 50);
        brestart.setBounds(120, 350, 100, 50);

        bstart.addActionListener(this);
        brestart.addActionListener(this);

        chuman1 = new JCheckBox("Human");
        chuman2 = new JCheckBox("Human");
        ccpu1 = new JCheckBox("Bot");
        ccpu2 = new JCheckBox("Bot");
        cexpert1 = new JCheckBox("Expert Bot");
        cexpert2 = new JCheckBox("Expert Bot");

        chuman1.setBounds(300, 100, 120, 40);
        chuman2.setBounds(500, 100, 120, 40);
        ccpu1.setBounds(300, 150, 120, 40);
        ccpu2.setBounds(500, 150, 120, 40);
        cexpert1.setBounds(300, 200, 120, 40);
        cexpert2.setBounds(500, 200, 120, 40);

        label = new JLabel("VS");
        label.setBounds(440, 150, 20, 50);
        label1 = new JLabel("Player Status : ");
        label1.setBounds(10, 50, 400, 40);
        score = new JLabel("score : x =           O = ");
        score.setBounds(300, 300, 200, 40);

        bg1 = new ButtonGroup();
        bg2 = new ButtonGroup();

        bg1.add(chuman1);
        bg1.add(ccpu1);
        bg1.add(cexpert1);
        bg2.add(chuman2);
        bg2.add(ccpu2);
        bg2.add(cexpert2);

        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
        add(b6);
        add(b7);
        add(b8);
        add(b9);

        add(bstart);
        add(brestart);

        add(chuman1);
        add(chuman2);
        add(ccpu1);
        add(ccpu2);
        add(cexpert1);
        add(cexpert2);
        add(label);
        add(label1);
        add(score);
        JOptionPane.showMessageDialog(null, "Do not interfere in the Game while Bots playing\n"
                + "Do Not change the Game opponent and start while in middle of the game\nchange the opponent after the Game is over\n"
                + "\nUse Restart to continue with previous Game for the Score to add up\nStart will erase the score \n"
                + "Please Do Not leave Game in middle");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        char a;
        if (player == 1) {
            a = 'X';
        } else {
            a = 'O';
        }
        int[] loc = new int[2];
        if (e.getSource() == bstart) {
            flag = false;
            sum = 0;
            scoreOfX = 0;
            scoreOfO = 0;
            score.setText("score : x =           O = ");
            if (chuman1.isSelected()) {
                sum += 0;
            }
            if (chuman2.isSelected()) {
                sum += 0;
            }
            if (ccpu1.isSelected()) {
                sum += 1;
            }
            if (ccpu2.isSelected()) {
                sum += 1;
            }
            if (cexpert1.isSelected()) {
                sum += 3;
            }
            if (cexpert2.isSelected()) {
                sum += 3;
            }
            flush();
            qu.clear();
            qu.add(loc);
            memuforplay(sum);

        } else if (e.getSource() == brestart) {
            flush();
            qu.add(loc);
            memuforplay(sum);
        } else if (e.getSource() == b1) {
            b1.setText(String.valueOf(a));
            b1.setEnabled(false);
            loc[0] = 0;
            loc[1] = 0;
            qu.add(loc);
        } else if (e.getSource() == b2) {
            b2.setText(String.valueOf(a));
            b2.setEnabled(false);
            loc[0] = 0;
            loc[1] = 1;
            qu.add(loc);
        } else if (e.getSource() == b3) {
            b3.setText(String.valueOf(a));
            b3.setEnabled(false);
            loc[0] = 0;
            loc[1] = 2;
            qu.add(loc);
        } else if (e.getSource() == b4) {
            b4.setText(String.valueOf(a));
            b4.setEnabled(false);
            loc[0] = 1;
            loc[1] = 0;
            qu.add(loc);
        } else if (e.getSource() == b5) {
            b5.setText(String.valueOf(a));
            b5.setEnabled(false);
            loc[0] = 1;
            loc[1] = 1;
            qu.add(loc);
        } else if (e.getSource() == b6) {
            b6.setText(String.valueOf(a));
            b6.setEnabled(false);
            loc[0] = 1;
            loc[1] = 2;
            qu.add(loc);
        } else if (e.getSource() == b7) {
            b7.setText(String.valueOf(a));
            b7.setEnabled(false);
            loc[0] = 2;
            loc[1] = 0;
            qu.add(loc);
        } else if (e.getSource() == b8) {
            b8.setText(String.valueOf(a));
            b8.setEnabled(false);
            loc[0] = 2;
            loc[1] = 1;
            qu.add(loc);
        } else if (e.getSource() == b9) {
            b9.setText(String.valueOf(a));
            b9.setEnabled(false);
            loc[0] = 2;
            loc[1] = 2;
            qu.add(loc);
        }

    }

    public static int gettotal(int[] location) {
        int total = 0;
        switch (location[0]) {
            case 0:
                total += 0;
                break;
            case 1:
                total += 3;
                break;
            case 2:
                total += 6;
        }
        switch (location[1]) {
            case 0:
                total += 1;
                break;
            case 1:
                total += 2;
                break;
            case 2:
                total += 3;
        }
        return total;
    }

    public static void setbutton(int[] location) {
        int total = gettotal(location);
        switch (total) {
            case 1:
                b1.doClick();
                return;
            case 2:
                b2.doClick();
                return;
            case 3:
                b3.doClick();
                return;
            case 4:
                b4.doClick();
                return;
            case 5:
                b5.doClick();
                return;
            case 6:
                b6.doClick();
                return;
            case 7:
                b7.doClick();
                return;
            case 8:
                b8.doClick();
                return;
            case 9:
                b9.doClick();
        }
    }

    public void flush() {
        qu.clear();
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        b4.setEnabled(true);
        b5.setEnabled(true);
        b6.setEnabled(true);
        b7.setEnabled(true);
        b8.setEnabled(true);
        b9.setEnabled(true);
        b1.setText("");
        b2.setText("");
        b3.setText("");
        b4.setText("");
        b5.setText("");
        b6.setText("");
        b7.setText("");
        b8.setText("");
        b9.setText("");
        label1.setText("Player Status : ");
    }

    public void memuforplay(int choice) {
        flag = true;
        SwingWorker<Integer, Void> worker0 = new SwingWorker<Integer, Void>() {
            int[] loc;
            grid a = new grid();

            @Override
            protected Integer doInBackground() throws Exception {
                qu.take();
                qu.clear();
                switch (choice) {
                    case 0:
                        human h1 = new human(1);
                        human h2 = new human(-1);
                        label1.setText("Player Status :- Human 1 : X      Human 2 : O");
                        while (a.full() == 0) {
                            if (flag == false) {
                                return 0;
                            }
                            if (a.check(player) == 1) {
                                a.win(player);
                                return player;
                            }
                            player = player * -1;

                            loc = qu.take();

                            if (player == 1) {
                                h1.play(a.arr, loc);
                            } else {
                                h2.play(a.arr, loc);
                            }
                        }
                        if (a.check(player) == 1) {
                            a.win(player);
                            return player;
                        } else {
                            JOptionPane.showMessageDialog(null, "Game Draw");
                        }
                        return 0;
                    case 1:
                        Robot r = new Robot(-1);
                        human h = new human(1);
                        label1.setText("Player Status :- Human : X      Bot : O");
                        while ((a.full() == 0)) {
                            if (flag == false) {
                                return 0;
                            }
                            if (a.check(player) == 1) {
                                a.win(player);
                                return player;
                            }

                            player = player * -1;

                            if (player == 1) {
                                loc = qu.take();
                                h.play(a.arr, loc);
                            } else {
                                r.playbot(a.arr, 1, 1);
                                setbutton(r.getlocation());
                                qu.take();
                            }
                        }
                        if (a.check(player) == 1) {
                            a.win(player);
                            return player;
                        } else {
                            JOptionPane.showMessageDialog(null, "Game Draw");
                        }
                        return 0;
                    case 2:
                        Robot r1 = new Robot(1);
                        Robot r2 = new Robot(-1);
                        label1.setText("Player Status :- Bot 1 : X      Bot 2 : O");
                        while ((a.full() == 0)) {
                            if (flag == false) {
                                return 0;
                            }
                            if (a.check(player) == 1) {
                                a.win(player);
                                return player;
                            }
                            player = player * -1;

                            if (player == 1) {
                                r1.playbot(a.arr, -1, 1);
                                setbutton(r1.getlocation());
                                qu.take();
                            } else {
                                r2.playbot(a.arr, 1, 1);
                                setbutton(r2.getlocation());
                                qu.take();
                            }
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                System.exit(1);
                            }
                        }
                        if (a.check(player) == 1) {
                            a.win(player);
                            return player;
                        } else {
                            JOptionPane.showMessageDialog(null, "Game Draw");
                        }
                        return 0;
                    case 3:
                        Expert_bot r3 = new Expert_bot(-1);
                        human h3 = new human(1);
                        label1.setText("Player Status :- Human 1 : X      Expert Bot  : O");
                        while ((a.full() == 0)) {
                            if (flag == false) {
                                return 0;
                            }
                            if (a.check(player) == 1) {
                                a.win(player);
                                return player;
                            }
                            player = player * -1;

                            if (player == 1) {
                                loc = qu.take();
                                h3.play(a.arr, loc);
                            } else {
                                r3.playbot(a.arr, 1, 0);
                                setbutton(r3.getlocation());
                                qu.take();
                            }
                        }
                        if (a.check(player) == 1) {
                            a.win(player);
                            return player;
                        } else {
                            JOptionPane.showMessageDialog(null, "Game Draw");
                        }
                        return 0;
                    case 4:
                        Expert_bot r4 = new Expert_bot(1);
                        Robot r5 = new Robot(-1);
                        label1.setText("Player Status :- Expert Bot  : X      Bot  : O");
                        while ((a.full() == 0)) {
                            if (flag == false) {
                                return 0;
                            }
                            if (a.check(player) == 1) {
                                a.win(player);
                                return player;
                            }
                            player = player * -1;

                            if (player == 1) {
                                r4.playbot(a.arr, -1, 0);
                                setbutton(r4.getlocation());
                                qu.take();
                            } else {
                                r5.playbot(a.arr, 1, 0);
                                setbutton(r5.getlocation());
                                qu.take();
                            }
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                System.exit(1);
                            }
                        }
                        if (a.check(player) == 1) {
                            a.win(player);
                            return player;
                        } else {
                            JOptionPane.showMessageDialog(null, "Game Draw");
                        }
                        return 0;
                    case 6:
                        Expert_bot r6 = new Expert_bot(1);
                        Expert_bot r7 = new Expert_bot(-1);
                        label1.setText("Player Status :- Expert Bot 1  : X     Expert Bot 2  : O");
                        while ((a.full() == 0)) {
                            if (flag == false) {
                                return 0;
                            }
                            if (a.check(player) == 1) {
                                a.win(player);
                                return player;
                            }
                            player = player * -1;

                            if (player == 1) {
                                r6.playbot(a.arr, -1, 0);
                                setbutton(r6.getlocation());
                                qu.take();
                            } else {
                                r7.playbot(a.arr, 1, 0);
                                setbutton(r7.getlocation());
                                qu.take();
                            }

                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                System.exit(1);
                            }
                        }
                        if (a.check(player) == 1) {
                            a.win(player);
                            return player;
                        } else {
                            JOptionPane.showMessageDialog(null, "Game Draw");
                        }
                        return 0;
                }
                return 0;
            }

            @Override
            protected void done() {
                super.done();
                flag = false;
                Integer a = null;
                try {
                    a = get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                if (a == 0) {
                    return;
                }
                if (a == 1) {
                    score.setText("score : x = " + (++scoreOfX) + "          O = " + scoreOfO);
                } else if (a == -1) {
                    score.setText("score : x = " + (scoreOfX) + "          O = " + (++scoreOfO));
                }
            }
        };
        worker0.execute();
    }
}

public class tic_tac_v2 {

    public static void main(String[] arg) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new gui();
            }
        });
    }
}
