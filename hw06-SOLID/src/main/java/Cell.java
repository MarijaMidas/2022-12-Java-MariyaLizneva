import java.util.ArrayList;
import java.util.List;

public class Cell implements CellInterface {
    private Nominal nominalCell;

    List<Nominal> countCell = new ArrayList<>();

    Cell(Nominal nominalCell) {
        this.nominalCell = nominalCell;
    }

    public void addNominal(Nominal nominal){
        countCell.add(nominal);
    }

    public void getNominal(Nominal nominal){
        countCell.remove(nominal);
    }

    public int quantity() {
        return countCell.size();
    }
}
