package com.thzy.social.utils.functionUtils.funtionInterface;

/**
 * @className: BranchHandleFunction
 * @description:
 * @author: TT-Berg
 * @date: 2022/10/11
 **/
@FunctionalInterface
public interface BranchHandleFunction {

    void doWhatHandle(ExecuteTaskFunction trueHandle, ExecuteTaskFunction falseHandle);
}
