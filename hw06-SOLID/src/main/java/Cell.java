import java.util.ArrayList;
import java.util.List;

public class Cell implements CellInterface {

    List<Nominal> countCell = new ArrayList<>();

    Cell(Cell cell){
        countCell = new ArrayList<>(cell.countCell);
    }

    public Cell() {
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
