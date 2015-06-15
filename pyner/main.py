from py4j.java_gateway import JavaGateway
from pymongo import MongoClient
from ner import NerHandler
import config
import time
import signal
import subprocess as sp

def close(sig, frame):
    java.kill()

if __name__ == '__main__':
    java_ner = ''
    global java
    java = sp.Popen(['java', '-jar', 'ner/ner_test.jar'])
    print('Waiting 1 min')
    print('NER server is starting')
    java_ner = JavaGateway().entry_point
    while True:
        try:
            if java_ner.check_started() == False:
                java_ner = JavaGateway().entry_point
                print('false')
                time.sleep(10)
            else:
                break;
        except:
            print('except')
            java_ner = JavaGateway().entry_point
            time.sleep(10)

    print('java server started')
    signal.signal(signal.SIGTERM, close)
    signal.signal(signal.SIGINT, close)
    db = MongoClient('mongodb://'+config.DBUSER+':'+config.DBPASSWD+'@'+config.DBHOST+':'+config.DBPORT)
    collection = db[config.DBCOLLECTION]
    table = collection[config.DBTABLE]

    Ner = NerHandler(table, java_ner)
    while True:
        print('do one')
        Ner.do_one()
