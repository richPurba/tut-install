package ethereumj.listeners;

import org.ethereum.core.Block;
import org.ethereum.core.TransactionReceipt;
import org.ethereum.facade.Ethereum;
import org.ethereum.listener.EthereumListener;
import org.ethereum.listener.EthereumListenerAdapter;
import org.ethereum.util.BIUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;

public class EthListener extends EthereumListenerAdapter{
    private Logger logs = LoggerFactory.getLogger(EthereumListener.class);
    private Ethereum ethereum;

    private boolean syncDone = false;
    private static final int oneThousand = 1000;

    private void out(String t){
        logs.info(t);
    }

    private String calcNetHashRate(Block block){
        String response = "Net has rate not available";
        if(block.getNumber() > oneThousand){
            long timeDelta = 0;
            for (int i = 0; i < oneThousand; ++i){
                Block parent = ethereum.getBlockchain().getBlockByHash(block.getParentHash());
                // why does this parent is instantiated every single loop?
                timeDelta += Math.abs(block.getTimestamp() - parent.getTimestamp());
            }
            response = String.valueOf(block
                    .getDifficultyBI()
                    .divide(BIUtil.toBI(timeDelta/oneThousand))
                    .divide(new BigInteger("10000000"))
                    .doubleValue())+"GHz";
        }
        return response;
    }

    public EthListener(Ethereum ethereum){
        this.ethereum = ethereum;
    }

    @Override
    public void onBlock(Block block, List<TransactionReceipt> receipts){
        if(syncDone){
            out("Net has rate: " + calcNetHashRate(block));
            out("Block difficulty: " + block.getDifficultyBI().toString());
            out("Block transactions: " + block.getTransactionsList().toString());
            out("Best block (last block: " + ethereum.getBlockchain().getBestBlock().toString());
            out("Total difficulty: " + ethereum.getBlockchain().getTotalDifficulty().toString());
        }

    }

    @Override
    public void onSyncDone(SyncState state){
        out("onSyncDone "+ state);
        if(!syncDone){
            out(" ** SYNC DONE ** ");
            syncDone = true;
        }
    }


}
