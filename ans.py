import numpy
import random
arr=[]
sol=[]
ans=[[] for i in range(7)]
def ofile(txtpath):
    fp=open(txtpath)
    for lines in fp.readlines():
        lines=lines.replace("\n","").split(",")
        arr.append(lines)
        fp.close()#read from text
    
def iput():
    f= raw_input("Enter your file path: ")
    ofile(f)
    s= raw_input("Enter your chosen patient: ")
    if not s.isdigit():
        print "Please input interger." #ensure interger input.
    return (int(s)) # string to interger
class Patient():

    def __init__(self, name, birth, score):
        self.name = name
        self.birth = birth
        self.score = score
    def __str__(self):
        return self.name
def cal():
    i=0
    j=0
    while(i<len(arr)):
        arr[i][0]=Patient(arr[i][0], arr[i][1],arr[i][2:23]) #store data into class
        for x in arr[i][0].score:
            ans[i].append(float(x)) #get the score from the text.
        i=i+1
    #print abs(numpy.cov([ans[1],ans[1]])[0,1]) #get Matrice de variance-covariance and the first row the second line is the vaule of covariance
    while j<len(arr):
        sol.append(float(abs(numpy.cov([ans[y],ans[j]])[0,1])))# caculate the covariance of the results of two different patients
        j=j+1
    del sol[y]  #delete chosen patient compared to itself.
    n=sol.index(min(sol)) # choose the closed patient.
    return arr[n][0].name #return patient's name

y=iput();
t=cal()
print t,"is the best"

