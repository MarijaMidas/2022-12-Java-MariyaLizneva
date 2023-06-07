
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Case implements CaseInterface {
    private Map<Integer,Cell> allCells = new TreeMap<>();
    private TreeMap<Integer,Cell> copyAllCells = allCells.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    Case(){
        for(Nominal nominal:Nominal.values()){
            allCells.put(nominal.getNominalValue(),new Cell(nominal));
        }
    }

    public Map<Integer, Cell> getMoney(int money) {
        Map<Integer,Cell> gettingMoney = new TreeMap<>();

        System.out.println(allCells.());

        for(Nominal nominal:Nominal.values()){
            gettingMoney.put(nominal.getNominalValue(),new Cell(nominal));
        }
        for(Map.Entry m: copyAllCells.entrySet()){
            if(money - (Integer) m.getKey()>=0 && ((Cell) m.getValue()).countCell.size()>0){
                money -= (Integer) m.getKey();
                ((Cell) m.getValue()).getNominal((Nominal) m.getKey());
            }
        }
        return gettingMoney;
    }

    public void put(Nominal nominal){
        allCells.get(nominal.getNominalValue()).addNominal(nominal);
    }

    public int getBalance() {
        int balance = 0;
        for(Map.Entry m: allCells.entrySet()){
            balance += (Integer) m.getKey() * ((Cell) m.getValue()).countCell.size();
        }
        return balance;
    }
}
