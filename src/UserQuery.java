public final class UserQuery extends Library implements Runnable {
    String[] queryData;
    int queryType;
    Thread t;

    public UserQuery(String userId, int queryType, String[] queryData) {
        this.queryType = queryType;
        this.queryData = new String[queryData.length];
        for (int i = 0; i < queryData.length; i++)
            this.queryData[i] = queryData[i];

        t = new Thread(this, userId);
        t.start();
    }

    public void run(){
        try{
            switch(queryType) {
                case 1: // 1-> Display All
                    search();
                    break;
                case 2:// 2 -> Search
                    System.out.println("Searching for book in Thread " + t.getName());
                    search(queryData[0], Integer.parseInt(queryData[1]));
                    break;
                case 3: // 3 -> Get Dues
                    System.out.println("Getting dues for user in Thread " + t.getName());
                    System.out.println("Student with ID : "+t.getName()+" has "+getDues(t.getName()) +" dues");
                    break;
                case 4: //4 -> Get On Hold Books
                    System.out.println("Checking number of books on hold for user in Thread " + t.getName());
                    System.out.println("Student with ID : "+t.getName()+" has "+booksHold(t.getName()) +" books on hold");
                    break;
                case 5: //5 -> Issue
                    System.out.println("Issueing book for user in Thread " + t.getName());
                    issue(t.getName(), queryData[0], queryData[1]);
                    break;
                case 6: //6 -> Reissue
                    System.out.println("Reissueing book for user in Thread " + t.getName());
                    reissue(t.getName(), queryData[0]);
                    break;
                case 7: //7 -> Return
                    System.out.println("Returning book for user in Thread " + t.getName());
                    bookreturn(queryData[0]);
                    break;
            }
        }catch(Exception e){
            System.out.println("Thread Interrupted");
            for (var s: e.getStackTrace())
                System.out.println(s);
        }
    }
}

