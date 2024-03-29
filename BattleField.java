public class BattleField {
    public static boolean fieldValidator(int[][] input) {
        int[][] field = input.clone();
        for(int i = 0; i < field.length; i++){
            for(int j = 0; j < field[0].length; j++){
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
        int battleships = 0;
        int cruisers = 0;
        int destroyers = 0;
        int submarines = 0;
        boolean parsed = false;
        field = pad(field);

        while(!parsed){
            boolean masterBreak = false;
            for (int i = 1; i < field.length - 1; i++) {
                for (int j = 1; j < field[i].length - 1; j++) {
                    if(field[i][j] == 1){
                        if(findNeighbours(field, i, j) >= 2) return false;
                        boolean isVertical = field[i - 1][j] == 1 || field[i + 1][j] == 1;
                        int blocks = 0;
                        if(isVertical){
                            for(int ii = 0; field[i + ii][j] == 1; ii++, blocks++){
                                if(findNeighbours(field, i + ii, j) > 1 && field[i + ii + 1][j] != 1) return false;
                                field[i + ii][j] = 0;
                            }
                        }
                        else{
                            for(int jj = 0; field[i][j + jj] == 1; jj++, blocks++){
                                if(findNeighbours(field, i, j + jj) > 1 && field[i][j + jj + 1] != 1) return false;
                                field[i][j + jj] = 0;
                            }
                        }
                        if(blocks == 1) submarines++;
                        else if(blocks == 2) destroyers++;
                        else if(blocks == 3) cruisers++;
                        else if(blocks == 4) battleships++;
                        else return false;

                        if(battleships > 1 ||
                                cruisers > 2 ||
                                destroyers > 3 ||
                                submarines > 4
                        ) return false;

                        masterBreak = true;
                        break;
                    }
                }
                if(masterBreak) break;
            }
            if(masterBreak) continue;
            return battleships == 1 ||
                    cruisers == 2 ||
                    destroyers == 3 ||
                    submarines == 4;
        }

        return false;
    }

    public static int[][] pad(int[][] arr){
        int[][] out = new int[arr.length + 2][arr[0].length + 2];
        for (int i = 1; i < out.length - 1; i++) {
            for (int j = 1; j < out[i].length - 1; j++) {
                out[i][j] = arr[i - 1][j - 1];
            }
        }

        return out;
    }

    public static int findNeighbours(int[][] arr, int i, int j){
        int neighbours = 0;
        for(int ii = -1; ii < 2; ii++){
            for(int jj = -1; jj < 2; jj++){
                if(ii == 0 && jj == 0) continue;
                if(arr[i + ii][j + jj] == 1) neighbours += 1;
            }
        }
        return neighbours;
    }
}
