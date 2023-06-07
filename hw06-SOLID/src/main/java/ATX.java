



import java.util.List;
import java.util.Map;

public class ATX implements ATXInterface {
    private Case myCase = new Case();

    public int getBalance() {
        return myCase.getBalance();
    }

    public void putMoney(List<Nominal> moneys){
        moneys.forEach(nominal -> myCase.put(nominal));
    }

    public void getMoney(int money){
        if(money%50 == 0){
            for(Map.Entry m: myCase.getMoney(money).entrySet()){
                if(((Cell) m.getValue()).countCell.size()!=0) {
                    System.out.printf("%d по %d рублей", ((Cell) m.getValue()).countCell.size(), m.getKey());
                }
            }
        }
        else if(getBalance()<money){
            throw new RuntimeException("Введите меньшую сумму");
        }else{
            throw new RuntimeException("Минимальная сумма должна быть кратна 50 руб.");
        }
    }
}
