package com.chansan.extension.runtime.process;

import com.chansan.extension.runtime.process.base.AbstractChainProcess;

/**
 * @name: CopyVideoAudioProcess
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.process.CopyVideoAudioProcess
 * @date: 2022/12/19
 * @description:
 */
public final class CopyVideoAudioProcess extends AbstractChainProcess {

    public CopyVideoAudioProcess(AbstractChainProcess next, String cmd) {
        super(next, cmd);
    }

    public CopyVideoAudioProcess(String cmd) {
        super(null, cmd);
    }


}
