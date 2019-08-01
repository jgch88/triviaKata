package trivia;

public class Player {
    private String _name;
    private int _place = 0;
    private int _gold = 0;
    private boolean _inPenaltyBox = false;

    public Player(String name) {
        _name = name;
    }

    public String name() {
        return _name;
    }

    public void addPenalty() {
        _inPenaltyBox = true;
    }

    public void removePenalty() {
        _inPenaltyBox = false;
    }

    public boolean isPenalized() {
        return _inPenaltyBox == true;
    }

    public void changePlace(int roll) {
        _place += roll;
        if (_place > 11) {
            _place = _place - 12;
        }

        System.out.println(_name + "'s new location is " + _place);
    }

    public int place() {
        return _place;
    }

    public void addGold() {
        _gold += 1;
        System.out.println(_name + " now has " + _gold + " Gold Coins.");
    }

    public boolean hitGoldTarget() {
        return _gold == 6;
    }
}
