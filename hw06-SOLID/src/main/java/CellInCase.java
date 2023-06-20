import java.util.ArrayList;
import java.util.List;

public class CellInCase implements Cell {

    List<Nominal> countCell = new ArrayList<>();

    CellInCase(CellInCase cell){
        countCell = new ArrayList<>(cell.countCell);
    }

    public CellInCase() {
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
