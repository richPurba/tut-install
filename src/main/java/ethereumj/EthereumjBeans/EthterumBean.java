package ethereumj.EthereumjBeans;

import org.ethereum.core.block;
import org.ethereum.facade.Ethereum;
import org.ethereum.facade.EthereumFactory;


public class EthterumBean {

    private Ethereum ethereum;

    public void start(){
        this.ethereum = EthereumFactory.createEthereum();
        this.ethereum.addListener();// make a listener.
    }
}
