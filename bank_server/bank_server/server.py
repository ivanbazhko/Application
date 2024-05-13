import psycopg2
import string
from tkinter import *
from tables_params import *
from validators import *
import socket
from _thread import *
import re
import json

conn = ''
class user:

    def __init__(self, card
                 )
class transaction:
    def __init__(self, cardnum, code, amount, airpacc):
        self.cardnum = cardnum
        self.code = code
        self.amount = amount
        self.airpacc = airpacc

def db_connect():
    try:
        conn = psycopg2.connect(
            database="bank",
            user="postgres",
            password="1",
            host="localhost",
            port="5432"
        )
        print("Successfully connected to PostgreSQL")
    except (Exception, psycopg2.Error) as error:
        print("Error while connecting to PostgreSQL", error)


def on_closing():
    finish = 0
    global conn
    try:
        conn.close()
        print("Successfully disconnected from PostgreSQL")
    except Exception:
        pass
    exit()

# функция для обработки каждого клиента
def client_thread (con):
    greeting = "welcome\r"
    print("Something connected")
    try:
        client_type = con.recv(1024).decode()          # получаем данные от клиента
        if client_type == "client":
            con.send("Successfully connected to bank".encode())
            print("Client connected")
            while 1:
                command = con.recv(1024).decode()

        elif client_type == "airport":
            con.send(greeting.encode())
            print("Airport connected")

            command = con.recv(1024).decode()
            data = json.loads(command)
            tr = transaction(data["cardnum"], data["code"], data["amount"],data["airpacc"])
            print(tr.cardnum,"  ", tr.code, "  ", tr.amount, "  ", tr.airpacc)
            con.send("success\r".encode())
        else:
            print("Disconnected")
            con.send("Error: client type".encode())
            con.close()

    except socket.error:
        print("Disconnected")
        con.close()
        exit()


db_connect()
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # создаем объект сокета сервера
hostname = "192.168.0.110"
port = 12345  # устанавливаем порт сервера
server.bind((hostname, port))  # привязываем сокет сервера к хосту и порту
server.listen(5)  # начинаем прослушиваение входящих подключений
socket.setdefaulttimeout(30)
print("Server running")
while True:
    client, _ = server.accept()  # принимаем клиента
    start_new_thread(client_thread, (client,))  # запускаем поток клиента


