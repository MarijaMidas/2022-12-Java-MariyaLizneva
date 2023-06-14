
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Case implements CaseInterface {
    private Map<Integer,Cell> allCells = new TreeMap<>();

    Case(){
        for(Nominal nominal:Nominal.values()){
            allCells.put(nominal.getNominalValue(),new Cell());
        }
    }

    public Map<Integer, Integer> getMoney(int money) {
        Map<Integer,Integer> gettingMoney = new TreeMap<>();

        Map<Integer,Cell> copyAllCells = allCells.entrySet().stream().map(it-> Map.entry(it.getKey(),new Cell(it.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue,newValue)->newValue,
                        TreeMap::new));

        Map<Integer,Cell> sortedCopyAllCells = new TreeMap<>(copyAllCells).descendingMap();

        for(Map.Entry<Integer,Cell> m: sortedCopyAllCells.entrySet()){
            int count = 0;
            while(money>=m.getKey()&&(m.getValue()).countCell.size() > 0) {
                if (money - m.getKey() >= 0) {
                    money -= m.getKey();
                    (m.getValue()).getNominal(Nominal.getNominal(m.getKey()));
                    count = count + 1;
                    gettingMoney.put(m.getKey(),count);
                }
            }
        }
        if(money>0) {
            throw new RuntimeException("В банкомате недостаточно купюр для выдачи запрошенной суммы");
        }
        allCells = sortedCopyAllCells;
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
