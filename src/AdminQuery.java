public final class AdminQuery extends AdminManagement implements Runnable{
    static int count;
    String[] queryData;
    int queryType;
    Thread t;

    public AdminQuery(String adminId, int queryType, String[] queryData) {
        try {
            this.queryType = queryType;
            Thread.sleep(2000);
            this.queryData = new String[queryData.length];
            for (int i = 0; i < queryData.length; i++)
                this.queryData[i] = queryData[i];

            t = new Thread(this, adminId);
            t.start();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void run(){
        try {
            switch (queryType) {
                case 1:// 1 -> Add Students
                    System.out.println("Adding Student of Thread " + t.getName());
                    addStudent(queryData[0], queryData[1], queryData[2],queryData[3]);
                    break;
                case 2: // 2 -> Remove Students
                    System.out.println("Removing Student of Thread " + t.getName());
                    removeStudent(queryData);
                    break;
                case 3: // 3 -> Add Books
                    System.out.println("Adding Book of Thread " + t.getName());
                    addBook(queryData[0], queryData[1], queryData[2]);
                    break;
                case 4: // 4 -> Remove Books
                    System.out.println("Removing Book of Thread " + t.getName());
                    removeBook(queryData[0], queryData[1]);

            }
        }catch(Exception e){
            System.out.println("Thread Interrupted");
            for (var s: e.getStackTrace())
                System.out.println(s);
        }
    }
}
