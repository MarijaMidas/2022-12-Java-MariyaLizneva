import java.util.Map;

public class MyCashMachine implements CashMachine {
    private final Case myCase = new CaseInATM();

    public int getBalance() {
        return myCase.getBalance();
    }

    public void putMoney(Map<Nominal,Integer>moneys) {
        for (Map.Entry<Nominal, Integer> map : moneys.entrySet()) {
            for(int i = map.getValue(); i>0; i--){
                myCase.put(map.getKey());
            }
        }
    }

    public  Map<Integer,Integer> takeMoneyOut(int money){
        if(money%50 == 0 && myCase.getBalance()>=money){
            return myCase.getMoney(money);
        }
        else if(myCase.getBalance()<money){
            throw new TakeMoneyOutException("В банкомате недостаточно средств");
        }else{
            throw new TakeMoneyOutException("Минимальная сумма должна быть кратна 50 руб.");
        }
    }

}
