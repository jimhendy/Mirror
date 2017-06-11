#! /usr/bin/env python

import os
import sys
import pandas as pd

def get_top_output():
    cmd = "top -n 1 -b"
    return os.popen(cmd).read().split("\n")

def get_java_pids():
    top = get_top_output()
    df = pd.DataFrame( [ l.split() for l in top[7:] ] )
    df.columns = top[6].split()
    df = df[ (df.USER=="jim")&(df.COMMAND.str.contains("java")) ]
    return df.PID.tolist()

def kill_pids( pids ):
    for p in pids:
        os.system("kill -KILL " + str(p) )
        pass
    pass

def main():
    kill_pids( get_java_pids() )
    pass

if __name__ == "__main__":
    main()
    pass
