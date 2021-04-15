package com.smoothstack.corejava.weekone;

public class Recursion {
    /**
     * Find if there exists a clump that may be summed up to the target.
     * @param start The array index to start with (usually 0)
     * @param nums The array
     * @param target The number to sum up to
     * @return Whether there exists a subset of numbers that may be summed up to target.
     */
    public static boolean groupSumClump(int start, int[] nums, int target) {
        //Base case
        if(start >= nums.length) return target == 0;

        //Recursive step
        int i = start;
        int sum = 0;

        //Sum up all members of the first clump of like numbers (even if it's only the one)
        while(i < nums.length && nums[start] == nums[i]) {
            sum += nums[i];
            i++;
        }

        //If the sum of the rest of the numbers gets us our total, return true
        if(groupSumClump(i, nums, target - sum)) return true;

        //Else, ignore the clump and move on
        return groupSumClump(i, nums, target);
    }
}
