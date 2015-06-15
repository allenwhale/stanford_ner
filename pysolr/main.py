import config
from pymongo import MongoClient
import sys

if __name__ == '__main__':
    db = MongoClient('mongodb://'+config.DBUSER+':'+config.DBPASSWD+'@'+config.DBHOST+':'+config.DBPORT)
    print(sys.argv)
