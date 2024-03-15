#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QDate>
#include <QVector>
#include "QDateTime"
#include <QtNetwork/QNetworkAccessManager>
#include <QRegularExpression>
#include <QCoreApplication>
#include <QNetworkReply>
#include <QNetworkRequest>
#include <qcustomplot.h>



QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:

    void on_pushButton_clicked();

    void replyFinished(QNetworkReply *reply);

    void on_currency1_currentIndexChanged(int index);

    void on_currency2_currentIndexChanged(int index);

    void on_date_range_currentIndexChanged(int index);

private:
    Ui::MainWindow *ui;
    QNetworkAccessManager *manager;
    unsigned int curr1 = 840;
    unsigned int curr2 = 643;
    QVector<QDate> x;
    QVector<double> y;
    QDate date;
    int date_range = 0;
    QString result;
};
#endif // MAINWINDOW_H
