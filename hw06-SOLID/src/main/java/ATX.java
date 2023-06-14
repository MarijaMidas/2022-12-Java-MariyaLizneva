



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
        if(money%50 == 0 && myCase.getBalance()>=money){
            for(Map.Entry<Integer,Integer> m: myCase.getMoney(money).entrySet()){
                if(m.getValue()!=0) {
                    System.out.println(m.getValue() + " по " + m.getKey() + " рублей.");
                }
            }
        }
        else if(myCase.getBalance()<money){
            throw new RuntimeException("Введите меньшую сумму");
        }else{
            throw new RuntimeException("Минимальная сумма должна быть кратна 50 руб.");
        }
    }

}
